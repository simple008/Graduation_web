package com.lukong.services;

import com.lukong.model.flink_web.Jars;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * Created by lukong on 16/10/8.
 * 与Flink UI通过RESTful API进行交互
 */
public class SpringRestClient {

    public static final String REST_SERVICE_URI="http://10.109.253.168:8081";


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
            System.out.println("jarId: "+jarFile.get("id")+" name:"+jarFile.get("name"));
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

        List<Map>jobs=res.get("jobs");

        /*排除cache这些策略任务*/

        /*思路：遍历List,若Map.get("name").equal("cache"),则排除该Map信息*/
//        for (Map item:jobs) {
//            String name= (String) item.get("name");
//            if(name.equals("cache")){
//                jobs.remove(item);
//            }
//        }---->会导致java.util.ConcurrentModificationException异常


        /*采用迭代器方式*/

        Iterator it=jobs.iterator();
        while (it.hasNext()){
            Map item= (Map) it.next();
            String name= (String) item.get("name");
            if(name.equals("cache")){
                it.remove();
            }
        }

        return jobs;
    }

    public List<Map> getJobsComp(){
        RestTemplate restTemplate=new RestTemplate();
        Map<String,List<Map>> res=restTemplate.getForObject(REST_SERVICE_URI+"/joboverview/completed",Map.class);
        List<Map>jobsComp=res.get("jobs");

        return jobsComp;
    }

    /*获取一个job处理数据的情况，例如读入多少字节，写入多少字节；读入多少记录，写入多少记录*/
    public List<Map> getMetrics(String jid){

        RestTemplate restTemplate=new RestTemplate();
        Map<String,Object>res=
                restTemplate.getForObject(REST_SERVICE_URI+"/jobs/{jid}/vertices/",Map.class,jid);
        List<Map> vertices= (List<Map>) res.get("vertices");

        List<Map>metrics=new ArrayList<>();
        for(int i=0;i<vertices.size();i++){
            Map item= (Map) vertices.get(i).get("metrics");
            metrics.add(item);
        }



        return metrics;
    }

    /*----POST-----*/

    /*运行组装好的协议适配器*/

    /*响应数据类型
    * {
         "jobid": "2c262866f234a3d96ed566ec7842ed13"
      }
    * */
    public  static Map run(String jarId,String entry_class,String program_args){
        System.out.println("run job...");

        Map<String,Object>uriVariables=new HashedMap();
        uriVariables.put("jarId",jarId);
        uriVariables.put("entry_class",entry_class);
        uriVariables.put("program_args",program_args);

        RestTemplate restTemplate=new RestTemplate();
        String test="test demo";

        Map res=restTemplate.postForObject(REST_SERVICE_URI+"/jars/{jarId}/run?entry-class={entry_class}&program-args={program_args}",
                test,Map.class,uriVariables);

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
        //getMetrics("47fef4b2910e2027742e3d42b106189f");
        run("d6d76c44-220f-4e38-965a-701a8d808d47_Graduation-1.0-SNAPSHOT.jar",
                "com.bupt.flink.apps.demo.dataRev.DataReceive","--test kafka --sensor ais --key f80a0d3a1bd37e211bb761a4d82254e9");
    }
}
