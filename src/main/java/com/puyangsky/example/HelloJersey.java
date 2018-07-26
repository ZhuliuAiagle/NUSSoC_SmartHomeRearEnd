package com.puyangsky.example;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import com.puyangsky.example.StartEntity;
import com.zijinshen.datadef.DataEngine;
import com.zijinshen.datadef.DeviceCondition;
import org.hibernate.Session;
import org.hibernate.Transaction;

//Path注解来设置url访问路径
@Path("/api")
public class HelloJersey {

    //GET注解设置接受请求类型为GET
    @GET
    //Produces表明发送出去的数据类型为text/plain
    //与Produces对应的是@Consumes，表示接受的数据类型为text/plain
    @Produces("text/plain")
    public String getString() {
        return "hello jersey!";
    }

    @Path("/getfanlevel")
    @GET
    @Produces("text/plain")
    public String GetFanLevel(){
        Session session = StartEntity.nowFactory.openSession();
        Transaction t = session.beginTransaction();
        DeviceCondition d = session.get(DeviceCondition.class,"001");
        int fl = d.getFanLevel();
        t.commit();
        session.close();
        String ret = Integer.toString(fl);
        System.out.println(fl);
        return ret;
    }

    @Path("/getlightlevel")
    @GET
    @Produces("text/plain")
    public String GetLightLevel(){
        Session session = StartEntity.nowFactory.openSession();
        Transaction t = session.beginTransaction();
        DeviceCondition d = session.get(DeviceCondition.class,"001");
        int l = d.getLightLevel();
        t.commit();
        session.close();
        String ret = Integer.toString(l);
        System.out.println(l);
        return ret;
    }
    @Path("/incfanlevel/{inc}")
    @POST
    @Consumes("text/plain")
    public Response IncFanLevel(@PathParam("inc") int inc){
        Session session = StartEntity.nowFactory.openSession();
        Transaction t = session.beginTransaction();
        DeviceCondition d = session.get(DeviceCondition.class, "001");
        if(d.getFanOpen() == 0){
            t.commit();
            return Response.status(Response.Status.BAD_REQUEST).entity("FAN_CLOSED").build();
        }
        int fl = d.getFanLevel();
        d.setFanLevel(fl+inc);
        t.commit();
        return Response.status(Response.Status.OK).entity("IEC_FAN_SUCCESS").build();
    }
    @Path("/decfanlevel/{dec}")
    @POST
    @Consumes("text/plain")
    public Response DecFanLevel(@PathParam("dec") int dec){
        Session session = StartEntity.nowFactory.openSession();
        Transaction t = session.beginTransaction();
        DeviceCondition d = session.get(DeviceCondition.class, "001");
        if(d.getFanOpen() == 0){
            t.commit();
            return Response.status(Response.Status.BAD_REQUEST).entity("FAN_CLOSED").build();
        }
        int fl = d.getFanLevel();
        int after = fl - dec;
        if(after < 0)
            return Response.status(Response.Status.BAD_REQUEST).entity("DEC_OVERFLOW").build();
        d.setFanLevel(after);
        t.commit();
        return Response.status(Response.Status.OK).entity("DEC_FAN_SUCCESS").build();
    }
    @Path("inclightlevel/{inc}")
    @POST
    @Consumes("text/plain")
    public Response IncLightLevel(@PathParam("inc") int inc){
        Session session = StartEntity.nowFactory.openSession();
        Transaction t = session.beginTransaction();
        DeviceCondition d = session.get(DeviceCondition.class, "001");
        if(d.getLightOpen() == 0){
            return Response.status(Response.Status.BAD_REQUEST).entity("LIGHT_CLOSED").build();
        }
        int fl = d.getLightLevel();
        d.setLightLevel(fl+inc);
        t.commit();
        return Response.status(Response.Status.OK).entity("INC_FAN_SUCCESS").build();
    }
    @Path("/declightlevel/{dec}")
    @POST
    @Consumes("text/plain")
    public Response DecLightLevel(@PathParam("dec") int dec){
        Session session = StartEntity.nowFactory.openSession();
        Transaction t = session.beginTransaction();
        DeviceCondition d = session.get(DeviceCondition.class, "001");
        if(d.getLightOpen() == 0){
            t.commit();
            return Response.status(Response.Status.BAD_REQUEST).entity("LIGHT_CLOSED").build();
        }
        int fl = d.getLightLevel();
        int after = fl - dec;
        if(after < 0)
            return Response.status(Response.Status.BAD_REQUEST).entity("DEC_OVERFLOW").build();
        d.setLightLevel(after);
        t.commit();
        return Response.status(Response.Status.OK).entity("DEC_LIGHT_SUCCESS").build();
    }
    @Path("/openfan/{level}")
    @POST
    @Consumes("text/plain")
    public Response OpenFan(@PathParam("level") int level){
        Session session = StartEntity.nowFactory.openSession();
        Transaction t = session.beginTransaction();
        DeviceCondition d = session.get(DeviceCondition.class, "001");
        int fl = d.getFanOpen();
        if(fl > 0){
            t.commit();
            return Response.status(Response.Status.BAD_REQUEST).entity("FAN_ALREADY_OPEN").build();
        }
        try {
            d.setFanOpen(1);
            d.setFanLevel(level);
            t.commit();
        }catch(Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.status(Response.Status.OK).entity("OPEN_FAN_SUCCESS").build();
    }
    @Path("/openlight/{level}")
    @POST
    @Consumes("text/plain")
    public Response OpenLight(@PathParam("level") int level){
        Session session = StartEntity.nowFactory.openSession();
        Transaction t = session.beginTransaction();
        DeviceCondition d = session.get(DeviceCondition.class, "001");
        int fl = d.getLightOpen();
        if(fl > 0){
            t.commit();
            return Response.status(Response.Status.BAD_REQUEST).entity("LIGHT_ALREADY_OPEN").build();
        }
        try {
            d.setLightOpen(1);
            d.setLightLevel(level);
            t.commit();
        }catch(Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.status(Response.Status.OK).entity("OPEN_LIGHT_SUCCESS").build();
    }
    @Path("/offlight")
    @POST
    @Consumes("text/plain")
    public Response OffLight(){
        Session session = StartEntity.nowFactory.openSession();
        Transaction t = session.beginTransaction();
        DeviceCondition d = session.get(DeviceCondition.class, "001");
        int fl = d.getLightOpen();
        if(fl == 0){
            t.commit();
            return Response.status(Response.Status.BAD_REQUEST).entity("LIGHT_ALREADY_OFF").build();
        }
        try {
            d.setLightOpen(0);
            d.setLightLevel(0);
            t.commit();
        }catch(Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.status(Response.Status.OK).entity("OFF_LIGHT_SUCCESS").build();
    }
    @Path("/offfan")
    @POST
    @Consumes("text/plain")
    public Response OffFan(){
        Session session = StartEntity.nowFactory.openSession();
        Transaction t = session.beginTransaction();
        DeviceCondition d = session.get(DeviceCondition.class, "001");
        int fl = d.getFanOpen();
        if(fl == 0){
            t.commit();
            return Response.status(Response.Status.BAD_REQUEST).entity("FAN_ALREADY_OFF").build();
        }
        try {
            d.setFanOpen(0);
            d.setFanLevel(0);
            t.commit();
        }catch(Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.status(Response.Status.OK).entity("OFF_FAN_SUCCESS").build();
    }
}