package com.tongering.coderunningsystem.service.impl.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tongering.coderunningsystem.mapper.ExerciseTestMapper;
import com.tongering.coderunningsystem.pojo.CodeRecord;
import com.tongering.coderunningsystem.pojo.ExerciseTest;
import com.tongering.coderunningsystem.service.impl.CodeRecordServiceImpl;
import com.tongering.coderunningsystem.util.Result;
import org.joor.Reflect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.function.Supplier;

@Component
public class Consumer extends Thread {
    private Code code;
    private static RestTemplate restTemplate;
    private final static String receiveCodeUrl = "http://127.0.0.1:5000/api/receive/code/result/";
    private final static String imp = "implements java.util.function.Supplier<Object>";

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate){
        Consumer.restTemplate = restTemplate;
    }

    public void startTimeout(long timeout, Code code){
        this.code = code;
        this.start();

        try{
            this.join(timeout);
        } catch (InterruptedException e){
            e.printStackTrace();
        } finally {
            this.interrupt();
        }
    }

    private String addUid(String code, String uid){
        String packagename = "package com.tongering.coderunningsystem.util;";
        String impo = "import java.util.Scanner;import java.io.File;import java.io.FileNotFoundException;";
        String template = "@Override\n" +
                "    public Object get() {\n" +
                "        File file = new File(\"input" + uid + ".txt\");\n" +
                "        try {\n" +
                "            Scanner sc = new Scanner(file);\n" +
                "            String input = sc.next();\n" +
                "            Object[] in = input.split(\",\");\n" +
                "            return result(in);\n" +
                "        } catch (FileNotFoundException e) {\n" +
                "            throw new RuntimeException(e);\n" +
                "        }\n" +
                "    }";
        int k = code.indexOf(" {");
        String result = packagename + impo + code.substring(0, k) + uid + " " + imp  + code.substring(k, k + 2) + template + code.substring(k+2);
        System.out.println(result);
        return result;
    }

    private String checkAns(Integer exerciseId, Supplier<Object> result, String uid){
        System.out.println("exerciseId:" + exerciseId);
        QueryWrapper<ExerciseTest> wrapper = new QueryWrapper<>();
        wrapper.eq("exercise_id", exerciseId);
        List<ExerciseTest> listraw = code.getList();
        System.out.println(listraw);

        for(ExerciseTest test : listraw){

            String input = test.getInput();
            String output = test.getOutput();

            File file = new File("input" + uid + ".txt");
            try(PrintWriter fout = new PrintWriter(file)){
                fout.println(input);
                fout.flush();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }


            System.out.println("id:" + test.getId() + "input:" + test.getInput() + "output:" + test.getOutput());

            if(compile(input, output, result).equals("error")){
                return test.getId().toString();
            }
        }
        return "success";
    }

    private String compile(String input, String output, Supplier<Object> result){
        Object[] in = input.split(",");
        Object object = result.get();
        System.out.println(object.toString());
        if(object.toString().equals(output)){
            return "success";
        }
        else {
            return "error";
        }
    }

    @Override
    public void run(){
        UUID uuid = UUID.randomUUID();
        String uid = uuid.toString().substring(0, 8);

        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("user_id", code.getUserId().toString());

        try{
            String codename = "com.tongering.coderunningsystem.util.Code" + uid;
            Supplier<Object> result = Reflect.compile(codename,
                    addUid(code.getCode(), uid)
            ).create().get();

            String check = checkAns(code.getExerciseId(), result, uid);

            if (check.equals("success")){
                data.add("result", "success");
                data.add("error_id", "-1"); // AC时



            } else if(check.equals("error")){
                data.add("result", "error");
                data.add("error_id", "0"); // 第一个就错误

            } else {
                data.add("result", "error");
                data.add("error_id", check); // 记录错误题号
            }

        } catch (Exception e){
            e.printStackTrace();
            data.add("result", "Compile error");
            data.add("error_id", "-2"); // 编译错误
        } finally {
            data.add("userId", code.getUserId().toString());
            data.add("ExerciseId", code.getExerciseId().toString());
            data.add("code", code.getCode());
            restTemplate.postForObject(receiveCodeUrl, data, String.class);
        }


    }

}
