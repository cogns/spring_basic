package com.beyond.basic.controller;

import com.beyond.basic.domain.Hello;
import com.beyond.basic.domain.Student;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

// 어노테이션은 스프링에서 미리 기능을 만들어둔 것
// 어노테이션은 @ 붙이는 것
// 그래서 몇십줄 코드를 다 줄일 수 있음
@Controller // 해당 클래스가 컨트롤러임을 명시
// 컨트롤러 : 사용자의 요청을 처리하고 응답하는 편의 기능

@RequestMapping("/hello") // 클래스 차원에 url매핑 시에 RequestMapping 사용,사용 시, 밑에 있는 Mapping들은 전부 /hello생략 가능

//@RestController // Controller + 각 메서드마다 @ResponseBody

public class HelloController {

//    ✨case1.✨ 사용자가 서버에게 화면요청(get)
    @GetMapping("/")



//    ✨case2.✨ @ResponseBody가 붙을경우 단순 String 데이터 return(get)
//     http://localhost:8080/hello 여기에 들어가면 "hello world" 띄워주는 프로그램 만든 것

//     getmapping을 통해 get요청을 처리하고 url패턴을 명시
//     responsebody를 사용하면 화면이 아닌 데이터를 return
//     만약 여기서 responsebody가 없고 return이 String이면 스프링은 resource, 폴더 밑의 templates폴더 밑의 helloworld.html 화면을 찾아 리턴
//     return "이름" 이렇게 생긴 거면 이름.html을 찾음. 이름이 똑같아야함. 띄어쓰기만 들어가도 에러뜸

    @ResponseBody //응답을 주겠다
    public String helloWorld(){
        return "helloworld";
    }



//    ✨case3.✨ 사용자가 json데이터 요청(get)
//     data를 리턴하되, json 형식으로 return
//     method명은 helloJson, url패턴은 /hello/json
    @GetMapping("/json")
    @RequestMapping(value = "/json", method= RequestMethod.GET) //메서드 차원에서도 RequestMapping 사용 가능
//     @ResponseBody //위의 RequestMapping과 같은 역할 그래서 둘이 같이 쓰면 에러남
//     responsebody를 사용하면서 객체를 return 시 자동으로 직렬화
    public Hello helloJson() throws JsonProcessingException {
        Hello hello = new Hello();
        hello.setName("hey");
        hello.setEmail("hey@hey");

//         아래 objectmapper로 직렬화 하는 거
//        ObjectMapper objectMapper = new ObjectMapper();
//        String value = objectMapper.writeValueAsString(hello);

        return hello;
    }



//    ✨case4.✨ 사용자가 json데이터를 요청하되, parameter형식으로 특정객체 요청
    // get 요청 중에 특정 데이터를 요청
    // 사용자 입장에서 id=~~이런 특별한 정보값을 달라고 하니까 getMapping 쓰는 것
    @GetMapping("/param1")
    @ResponseBody
//    parameter형식 : /param1?name=hongildong
    public Hello Param1(@RequestParam(value = "name")String inputName){
        Hello hello = new Hello();
        hello.setName(inputName);
        hello.setEmail("hong@naver.com");
        System.out.println(hello);
        return hello;
    }
//    url패턴 : model-param2, 메서드 : modelParam2
//    parameter2개 : name, email -> hello객체 생성후 리턴
//    요청방식 : /param2?name=xxx&email=xxx.naver.com
    @GetMapping("/param2")
    @ResponseBody
    public Hello Param2(@RequestParam(value = "name")String name,
                        @RequestParam(value = "email")String email){
        Hello hello = new Hello();
        hello.setName(name);
        hello.setEmail(email);
        return hello;
    }



//    ✨case5.✨ parameter형식으로 요청하되, 서버에서 데이터바인딩 하는 형식
//    parameter가 많을 경우 객체로 대체가 가능. 객체에 각 변수에 맞게 알아서 바인딩(객체바인딩)
//    ?name=xxx&email=xxx.naver.com&password=xxxx
//    데이터바인딩 조건 : 기본생성자, setter가 있어야 된다.
    @GetMapping("/param3")
    @ResponseBody
    public Hello Param3(Hello hello){
        return hello;
    }



//    ✨case6.✨ 서버에서 화면에 데이터를 넣어 사용자에게 return(model객체 사용)
//     model객체에 name이라는 키값에 value를 세팅하면 해당 key:value는 화면으로 전달
    @GetMapping("/model-param")
    public String modelParam(@RequestParam(value = "name")String inputName, Model model){
        model.addAttribute("name", inputName);
        return "helloworld";
    }



//    ✨case7.✨ pathvariable방식을 통해 사용자로부터 값을 받아 화면 리턴
//    localhost:8080/hello/model-path/cogns
//    @Pathvariable방식은 url을 통해 자원의 구조를 명확하게 표현함으로, 좀 더 restful한 형식
    @GetMapping("/model-path/{name}")
    public String modelPath(@PathVariable String name, Model model){
        model.addAttribute("name", name);
        return "helloworld";
    }



    @GetMapping("/form-view")
//    사용자에게 name, email, password를 입력할 수 있는 화면을 주는 메서드 정의
//    메서드명 : form-view.html, 화면명 : form-view
    public String formView(){
        return "form-view";
    }
//    post요청(사용자입장에서 서버에 데이터를 주는 상황)
//    case1. url인코딩 방식(text만) 전송
//    형식 : key1=value1&key2=value2&key3=value3
    @PostMapping("/form-post1")//getmapping이랑 같은 url 사용 가능
    @ResponseBody
    public String formPost1(@RequestParam(value="name") String inputName,
                            @RequestParam(value="email" )String inputEmail,
                            @RequestParam(value="password") String inputPW ){
        Hello hello=new Hello();
        hello.setName(inputName);
        hello.setPassword(inputPW);
        hello.setEmail(inputEmail);
        System.out.println(hello);
        return "ok";

    }



//      ✨post-case1✨ 객체로 받는 방식 //위에 있는 post1이랑 같은 내용인데 훨씬 간결한 코드
//      ModelAttribute 붙여도 되고 안 붙여도 됨
//      데이터 바인딩을 명시하는 것 for 가독성
    @GetMapping("/form-post2")
    public String formView2(){
        return "form-view";
    }
    @PostMapping("/form-post2")
    @ResponseBody
    public String formPost2(@ModelAttribute Hello hello){
        System.out.println(hello);
        return "ok";
    }



//      ✨post-case2✨ : multipart/form-data 방식(text와 파일 전송)
    // url명 : form-file-post, 메서드명 : formFilePost, 화면명 : form-file-view
    // form 태그 name, email, password, file
    @GetMapping("form-file-post")
    public String formFileView(){
        return "form-file-view";
    }

    @PostMapping("/form-file-post")
    @ResponseBody
    // @RequestParam(value="name") String inputName,
    // @RequestParam(value="email" )String inputEmail,
    // @RequestParam(value="password") String inputPW
    // 위에 있는 코드를 Hello hello로 대신 받는 것(photo는 String가 아니라 x)
    public String formFilePost(Hello hello,
                               @RequestParam(value = "photo")MultipartFile photo){
        System.out.println(hello);
        System.out.println(photo.getOriginalFilename());
        return "ok";
    }



//      ✨post-case3✨ : js를 활용한 form 데이터 전송(text)
    @GetMapping("/axios-form-view")
    public String axiosFormView(){
        // name, email,password를 전송
        return "axios-form-view";
    }

    @PostMapping("/axios-form-view")
    @ResponseBody
    public String axiosFormPost(Hello hello){
        System.out.println(hello);
        return "ok";
    }



//      ✨post-case4.✨ js를 활용한 from 데이터 전송(+file)
    @GetMapping("/axios-form-file-view")
    public String axiosFormFileView(){
        return "axios-form-file-view";
    }

    @PostMapping("/axios-form-file-view")
    @ResponseBody
    public String axiosFormFileViewPost(Hello hello,
                                        @RequestParam(value = "file")MultipartFile file){
        System.out.println(hello);
        System.out.println(file.getOriginalFilename());
        return "ok";
    }



//      ✨post-case5.✨ js를 활용한 json 데이터 전송
//  url패턴 : axios-json-view, 화면명 : axios-json-view,
//  get요청메서드 : 동일, post요청메서드 : axiosJsonPost
    @GetMapping("/axios-json-view")
    public String axiosJsonView(){
        return "axios-json-view";
    }

    //    json으로 전송한 데이터를 받을 때엔는 @RequestBody 어노테이션 사용
    @PostMapping("/axios-json-view")
    @ResponseBody
    public String axiosJsonPost(@RequestBody Hello hello){
        System.out.println(hello);
        return "ok";
    }



//      ✨post-case6.✨ js를 활용한 json 데이터 전송(+file)
    @GetMapping("/axios-json-file-view")
    public String axiosJsonFieView(){
        return "axios-json-file-view";
    }

//    RequestPart는 file과 json을 처리할 떄 주로 사용하는 어노테이션
    @PostMapping("/axios-json-file-view")
    @ResponseBody
    public String axiosJsonFiePost(
//                                   @RequestParam("hello") String hello,
//                                   @RequestParam("file") MultipartFile file
//                                    formdata를 통해 json, file(멀티미디어)을 처리할 때 RequestPart 어노테이션을 사용한다
                                    @RequestPart("hello") Hello hello,
                                    @RequestPart("file") MultipartFile file){
////        String으로 받은 뒤 수동으로 객체로 변환
//        ObjectMapper objectMapper = new ObjectMapper();
//        Hello h1 = objectMapper.readValue(hello, Hello.class);
//        System.out.println(h1.getName());
        System.out.println(hello);
        System.out.println(file.getOriginalFilename());
        return "ok";
    }



//      ✨post-case7.✨ js를 활용한 json 데이터 전송(+여러 file)
    @GetMapping("/axios-json-multi-file-view")
    public String axiosJsonMultifileFieView(){
        return "axios-json-multi-file-view";
    }

    @PostMapping("/axios-json-multi-file-view")
    @ResponseBody
    public String axiosJsonMultifileFiePost(@RequestPart("hello") Hello hello,
                                            @RequestPart("files") List<MultipartFile> files){
        System.out.println(hello);
        for (MultipartFile file : files){
            System.out.println(file.getOriginalFilename());
        }
        return "ok";
    }



//      ✨post-case8.✨ 중첩된 Json데이터 처리
//      {name:'hongildong', email:'hong@naver.com', scores:[{math:60}, {music:70}, {english:100}]};

    @GetMapping("/axios-nested-json-view")
    public String axiosNestedJsonView(){
        return "axios-nested-json-view";
    }

    @PostMapping("/axios-nested-json-view")
    @ResponseBody
    public String axiosNestedJsonPost(@RequestBody Student student){
        System.out.println(student);
        return "ok";
    }


}

