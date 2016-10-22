package com.lukong.services;

import com.lukong.model.flink_web.Jars;
import com.lukong.model.flink_web.Job;
import org.apache.commons.collections.map.HashedMap;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.jar.JarFile;

/**
 * Created by lukong on 16/10/8.
 * 与Flink UI通过RESTful API进行交互
 */
public class SpringRestClient {

    public static final String REST_SERVICE_URI="http://localhost:8081";


    /*-----GET-----*/

    /*获取第一jar包的信息*/
    public  Map getJars(){
        System.out.println("list jars....");
        RestTemplate restTemplate=new RestTemplate();
        Jars jars=restTemplate.getForObject(REST_SERVICE_URI+"/jars",Jars.class);
        //System.out.println("address: "+jars.getAddress());
        List<Map<String,Object>>jarFiles=jars.getFiles();
        Iterator iterator=jarFiles.iterator();
        Map jarFile=null;
        if (iterator.hasNext()){
            jarFile= (Map) iterator.next();
            System.out.println("id: "+jarFile.get("id")+" name:"+jarFile.get("name"));
        }
        return jarFile;
    }

    /*获取Flink集群信息*/
    public  void getInfo(){
        System.out.println("info...");
        RestTemplate restTemplate=new RestTemplate();
        HashMap<String,Object>info=restTemplate.getForObject(REST_SERVICE_URI+"/config", HashMap.class);
        System.out.println("flink-version: "+info.get("flink-version") +" timezone-name: "+info.get("timezone-name"));
    }

    /*获取协议执行图*/
    public void getPlan(String jarId,String entry_class,String program_args){
        System.out.println("get plan...");

        RestTemplate restTemplate=new RestTemplate();
        HashMap<String,HashMap<String,Object>>planMap=restTemplate.getForObject(REST_SERVICE_URI+"/jars/{jarId}/plan?entry-class={entry_class}&program-args={program_args}",
                HashMap.class,jarId,entry_class,program_args);

        String name= (String) planMap.get("plan").get("name");
        List<Map<String,Object>>nodes= (List<Map<String, Object>>) planMap.get("plan").get("nodes");
        String desc= (String) nodes.get(0).get("description");
        System.out.println(name);
        System.out.println(desc);

    }

    /*停止运行中的任务*/
    public void cancel(String jobid){

        RestTemplate restTemplate=new RestTemplate();
        String res=
                restTemplate.getForObject(REST_SERVICE_URI+"/jobs/{jobId}/yarn-cancel",String.class,jobid);

    }

    public  List<Map> getJobs(){

        RestTemplate restTemplate=new RestTemplate();
        Map<String,List<Map>> res=restTemplate.getForObject(REST_SERVICE_URI+"/joboverview/running",Map.class);

        //Map map= res.get("jobs").get(0);
        List<Map>list=res.get("jobs");
        //System.out.println(map.get("name"));

        return list;
    }

    public List<Map> getJobsComp(){
        RestTemplate restTemplate=new RestTemplate();
        Map<String,List<Map>> res=restTemplate.getForObject(REST_SERVICE_URI+"/joboverview/completed",Map.class);
        List<Map>list=res.get("jobs");
        return list;
    }

    /*获取一个job处理数据的情况，例如读入多少字节，写入多少字节；读入多少记录，写入多少记录*/
    public void getMetrics(String jid){

        RestTemplate restTemplate=new RestTemplate();
        restTemplate.getForObject(REST_SERVICE_URI+"/jobs/{jid}/vertices/",Map.class,jid);
    }

    /*----POST-----*/

    /*运行组装好的协议适配器*/

    /*响应数据类型
    * {
         "jobid": "2c262866f234a3d96ed566ec7842ed13"
      }
    * */
    public  Map run(String jarId,String entry_class,String program_args){
        System.out.println("run job...");

        Map<String,Object>uriVariables=new HashedMap();
        uriVariables.put("jarId",jarId);
        uriVariables.put("entry_class",entry_class);
        uriVariables.put("program_args",program_args);

        RestTemplate restTemplate=new RestTemplate();
        String test="test demo";
        Map res=restTemplate.postForObject(REST_SERVICE_URI+"/jars/{jarId}/run?entry-class={entry_class}&program-args={program_args}",
                test,Map.class,uriVariables);

        //System.out.println(res.get("jobid"));

        return res;
    }

    /*上传开发好的协议JAR包文件*/
    public void upload() throws IOException {

        System.out.println("upload...");
        /*应该形参为二进制流文件？*/

        FileInputStream in=new FileInputStream(new File("/Users/lukong/Desktop/svn/Graduation/target/Graduation-1.0-SNAPSHOT.jar"));
        byte[] buffer=new byte[10240000];
        in.read(buffer);

        //JarFile jarFile=new JarFile(new File("/Users/lukong/Desktop/svn/Graduation/target/Graduation-1.0-SNAPSHOT.jar"));

        RestTemplate restTemplate=new RestTemplate();
        String res=restTemplate.postForObject(REST_SERVICE_URI+"/jars/upload",
                buffer,String.class);

        System.out.println(res);
    }



    /*------DELETE-------*/

    public static void main(String ...args){
    }
}
