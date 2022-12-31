package com.tongering.coderunningsystem.service.impl.util;

import com.tongering.coderunningsystem.pojo.ExerciseTest;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class CodePool extends Thread{
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition(); // 条件变量
    private final Queue<Code> codes = new LinkedList<>();
    public void addCode(Integer userId, String code, Integer exerciseId, List<ExerciseTest> exelist, Double time){
        lock.lock();
        try {
            codes.add(new Code(userId, code, exerciseId, exelist, time));
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }
    private void consume(Code code){
        Consumer consumer = new Consumer();
        consumer.startTimeout(code.getTime().longValue() + 1000, code);
    }
    @Override
    public void run() {
        while(true){
            lock.lock();
            if(codes.isEmpty()){ // 当bots为空，则需要把当前线程阻塞住
                try {
                    condition.await(); // 阻塞住当前线程，直到当前线程被唤醒(执行signal)或者被销毁
                    // await方法自动包含解锁操作
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    lock.unlock();
                    break;
                }
            } else {
                Code code = codes.remove();
                lock.unlock();

                consume(code); // 比较耗时，故放在lock后面
            }
        }
    }
}
