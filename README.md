## Online Judge 系统平台的设计与实现

项目演示地址：http://112.74.36.87/

![maven](https://img.shields.io/badge/Build-tongering-blue.svg) ![maven](https://img.shields.io/badge/Test-tongering-yellow.svg)

---

### 项目的研究目的与意义

- 提升教育质量📈：通过Online Judge系统，学生可以进行`在线编程练习`和`自动评测`，获得实时反馈。这不仅有助于提高编程技能，还让学生能够`更加专注于学习本身`，`而非环境配置`。系统的多设备兼容性进一步方便学生在不同设备上练习，`加深对计算机科学知识的理解`。

- 实现教育资源共享📂：此系统能够帮助教育机构或高校之间`共享编程资源和题库`，促进教育资源的共享与合作，提高教学效率。在线评测系统的特性还`允许个性化定制题目`，满足不同教学需求。

- 促进学生多元化发展📊：鉴于国内学生普遍使用Windows系统，而计算机领域学习Linux系统是必不可少的，本项目包含`在线terminal模块`，旨在提升学生的`编码能力和算法技能`，并增进对`Linux系统命令`的掌握，推动学生技能的全面发展。

### 研究内容

1. 在系统架构设计研究Online Judge系统的`整体架构设计`，包括`前端`和`后端`的组件，`数据存储`等。探讨不同组件之间的交互方式，以满足系统性能、可维护性和可扩展性的要求。

2. 在前端技术和用户界面设计上选择适合的前端技术，如`Vue`、`Element`、`Bootstrap`，以及编辑器组件如`md-editor`、`Monaco Editor`的集成。讨论如何提供用户友好的界面，让学生和教师能够轻松访问和使用系统。

3. 在后端技术和数据管理上考察后端技术，如`Spring Boot`、`MyBatis-Plus`、`Spring Security`的应用。研究`数据库设计`、数据模型，以及如何处理用户数据、题目数据和评测结果。

4. 在安全性和用户认证上分析系统的安全性措施，特别关注`用户密码加密`、`用户身份认证`、数据隐私保护等方面。探讨如何保护系统免受恶意攻击和数据泄露的威胁。

5. 在在线代码评测和自动评分上深入研究在线代码评测的算法和技术，如何实现`自动评分`、`错误提示`、`性能分析`等功能。探讨评测机制的效率和准确性。

6. 在编程语言和题目库上研究系统支持的编程语言范围，以及如何构建和管理题目库。讨论题目的多样性和难度分级。

7. 在用户体验和可访问性上研究如何提供良好的用户体验，包括`响应式设计`、`跨平台支持`、辅助功能，以确保系统对不同用户群体都友好和易用。

8. 在运行代码安全措施上探讨如何在在线评测中采取的保护虚拟机`防止恶意代码`的措施。



### 整体平台系统框架

<img src="https://tongeringmd.oss-cn-guangzhou.aliyuncs.com/website/kaoyan/202401161629112.png" alt="image-20240116162948065" style="zoom: 67%;" />

#### 客户端

`User` 多个用户浏览器

#### 服务器

`Web` 负责前端页面的分发以及Nginx的负载均衡

`backend_system` 负责主要后端的工作，例如：登录授权验证、用户各类信息的分发、题目信息的分发。

`code_running_system` 负责用户代码校验模块，通过提取数据库相关题目测试样例与用户提交代码进行运行测试，并返回结果至backend_system

`MySQL` 负责数据库信息CRUD

`Terminal` 每个用户所分配的linux系统



### 功能模块介绍

### Terminal模块

##### 前端

![image-20240118161545120](https://tongeringmd.oss-cn-guangzhou.aliyuncs.com/website/kaoyan/202401181615177.png)

该模块借助`Xterm.js`第三方工具 [Xterm.js (xtermjs.org)](https://xtermjs.org/)

在`html`页面中置入

```html
<div id="xterm" class="xterm" />
```

在`js`页面中导入

```js
import 'xterm/css/xterm.css'
import { Terminal } from 'xterm'
import { FitAddon } from 'xterm-addon-fit'
import { AttachAddon } from 'xterm-addon-attach'
```

通过`WebSocket`链接后端服务器端口路径

```js
let socketUrl = `ws://${store.state.url.ip}:${store.state.url.port}/websocket/xterm/${store.state.user.token}/`
```

初始化`Xterm`

```js
const initTerm = () => {
    term = new Terminal({
        fontSize: 14, // 字体大小
        cursorBlink: true // 光标是否闪烁
    });
    const attachAddon = new AttachAddon(socket); // 通过WebSocket连接后端
    const fitAddon = new FitAddon(); // 自适应窗口
    term.loadAddon(attachAddon);
    term.loadAddon(fitAddon);
    term.open(document.getElementById('xterm'));
    term.focus()
    term.writeln('Connecting...');
    write_term()

};
```

Xterm.js的API使用：[xterm.js-API](https://github.com/xtermjs/xterm.js/blob/master/typings/xterm.d.ts)

##### 后端

除了创建对应WebSocket链接外，还需创建SSH链接组件`SSHConnection.java`用于与linux服务器连接的java类，其中使用`jsch`库，用于在Java中进行SSH连接。它允许Java应用程序通过SSH协议与远程服务器进行通信，执行命令，传输文件等。

为应对多名用户同时使用linux服务器所创建的SSH连接数，故采用java工具类`concurrent`进行并发编程，使得在多线程环境中编写安全和高效的代码更容易。应用了线程池中的`ExecutorService`

![image-20240118190633703](https://tongeringmd.oss-cn-guangzhou.aliyuncs.com/website/kaoyan/202401181906775.png)

`SSHConnectionInit`负责初始化与Linux系统的链接，使用到`Init`来初始化Session会话以及Channel通道，使用`connect`去连接服务器，借助`transToSSH`发送信息给Linux服务器，借助`sendMessage`向客户端发送信息，此时已初始化完成。

`SSHCommand`负责接收用户输入的指令，借助`Init`初始化Channel通道，借助`transToSSH`因在`connect`中开启了一个线程，此时只要有信息在Session中就会将信息借助`sendMessage`发送给客户端。

`close`负责断开当前用户所创建的所有Session和Channel。

### 在线代码评测模块

#### 前端

![image-20240119103716239](https://tongeringmd.oss-cn-guangzhou.aliyuncs.com/website/kaoyan/202401191037346.png)

借助`Monaco-editor`库来实现右侧代码模块，借助`md-editor`库来实现左侧题目模块

`Monaco-editor`库地址：[Monaco Editor](https://microsoft.github.io/monaco-editor/)

在html页面中置入

```html
<div id="codeEditBox">{{ testcode }}</div>
```

在js中置入

```js
import * as monaco from 'monaco-editor'
```

借助组件`initEditor`实现初始化代码编辑器

```js
const initEditor = () => {
    // 初始化编辑器，确保dom已经渲染
    editor.value = monaco.editor.create(document.getElementById('codeEditBox'), {
        value: template.value, //编辑器初始显示文字
        language: 'java', //此处使用的java，其他语言支持自行查阅demo
        theme: 'vs', //官方自带三种主题vs, hc-black, or vs-dark
        selectOnLineNumbers: true,//显示行号
        roundedSelection: false,
        readOnly: false, // 只读
        cursorStyle: 'line', //光标样式
        automaticLayout: true, //自动布局
        glyphMargin: true, //字形边缘
        useTabStops: false,
        fontSize: 15, //字体大小
        autoIndent: true, //自动布局
        quickSuggestionsDelay: 100, //代码提示延时
    });
}
```

`md-editor`库地址：[Editor.md - 开源在线 Markdown 编辑器](https://pandao.github.io/editor.md/)

#### 后端

在本项目中为实现两个服务之间的通信采用了`Spring boot`中的`RestTemplate`组件，是 Spring Framework 提供的用于进行 HTTP 请求的模板类。常在分布式系统中使用，其具有轻量性和灵活性。

需要在`pom.xml`中导入Maven项目

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

并创建`RestTemplateConfig.java`注入bean

```java
@Configuration
public class RestTemplateConfig {
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
```

定义URL

```java
private final static String addCodeUrl = "http://127.0.0.1:5001/api/code/add/";
```

发送数据

```java
CodeRunningSocketServer.restTemplate.postForObject(addCodeUrl, data, String.class);
```

这里会因HTTP请求其他服务故涉及到多线程问题，故在向前端发送信息时需要`synchronized`

服务`backend`发送信息至`code_running_system`

`code_running_system`是专门用来运行代码，将相关样例输入并检查结果的服务，为提高运行质量和速度，采用多线程模式。

代码运行模块核心部分：code_running_system下的`CodePool`和`Consumer`模块

![image-20240119141026572](https://tongeringmd.oss-cn-guangzhou.aliyuncs.com/website/kaoyan/202401191410640.png)

`CodePool.java`指线程池，为每一个在队列里的代码都分配一个线程去执行代码。

`Consumer.java`指实际运行代码模块，

这里因执行代码时间需要有时间控制，即时间中断，若超时则自动结束当前线程，避免恶意代码浪费服务器资源，故在本项目中采用线程的`join`方法，超时即终止。

`addUid`在java类名后加入一些随机数，以确保并发情况下代码运行模块时读取文件是唯一的。

`checkAns`模块涉及到IO读写，将用户输入的代码保存为一个文件，在运行代码时再将其读取出来，为后面转为Docker运行代码奠定了一定的基础。

> 本版本采用java反射来实现
>
> 下个版本升级为Docker运行代码模式，可以支持更多的语言以及更加贴近实际在线判题模式

### OSS云对象存储

本项目采用`阿里云OSS云对象存储`，之所以不采用将图片文件存储至本服务器上，主要是杜绝恶意文件仅通过修改后缀名来绕开前后端检测而对服务器造成一定的危险。

需添加Maven项目依赖

```xml
<dependency>
    <groupId>com.aliyun.oss</groupId>
    <artifactId>aliyun-sdk-oss</artifactId>
    <version>3.15.0</version>
</dependency>
```

本项目采用`日期 + UUID + 后缀名`的方式来组成文件名，很大程度上杜绝了文件名重复问题。

```java
String fileName = data.getOriginalFilename(); // 获取传过来的文件名
String subfixFile =  fileName.substring(fileName.lastIndexOf(".")); // 获取文件的后缀名

UUID id = UUID.randomUUID();
String[] idd = id.toString().split("-"); // 获取UUID格式化

SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
String formatTime = simpleDateFormat.format(new Date()); // 获取格式化日期

String newFileName = formatTime + idd[0] + subfixFile; // 通过UUID和后缀名拼接新的文件名
```

### Springboot用户密码存储

采用Spring Security`密码哈希`和`加盐`的方式进行密码加密，从而提高安全性。

`密码哈希`则是使用哈希函数对用户密码进行单向哈希，即无法通过哈希值还原出原始密码。

`加盐`则是一个随机生成的值，它与密码合并后再进行哈希。每个用户都有一个唯一的盐，即使用户密码相同，由于盐的不同，哈希值也会不同。从而防止彩虹表攻击。

### 个性化用户推荐

> 暂时采用随机算法，将来可对接Flink、Spark等实时处理框架和相关推荐算法来进行替换。

### 用户身份

采用学生、教师、管理员身份，通过查询SQL表来获取对应用户身份。

![image-20240119214752692](https://tongeringmd.oss-cn-guangzhou.aliyuncs.com/website/kaoyan/202401192147801.png)

管理员身份

![image-20240119215150427](https://tongeringmd.oss-cn-guangzhou.aliyuncs.com/website/kaoyan/202401192151518.png)

教师身份

### 帖子、练习检索

采用SQL模糊查询来检索帖子或练习的关键字。
