package com.example.covidfight;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.ArrayList;

public class VcuStat extends AppCompatActivity {

    private TextView activeStudentCaseNumber,activeEmployeeCaseNumber,isolationNumber,quarantineNumber,negativeEntryResultNumber,positiveEntryResultNumber,negavtivePrevelenceNumber,positivePrevelenceNumber;
    int studentCase, employeeCase,isoNumber,quaNumber,negativeEntryNum,positiveEntryNum,negPrevelenceNum,posPrevelenceNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vcu_stat);

        getData();



    }


   private  void getData(){
       final Gson gson= new Gson();

       String url="https://quinn50.dev/vcucovid/api/v1";


       StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

           @Override
           public void onResponse(String response) {

               activeStudentCaseNumber=findViewById(R.id.ActiveStudentCaseNumber);
               activeEmployeeCaseNumber=findViewById(R.id.ActiveEmployeeCaseNumber);
               isolationNumber=findViewById(R.id.IsolationNumber);
               quarantineNumber=findViewById(R.id.QuarantineNumber);
               negativeEntryResultNumber=findViewById(R.id.NegavtiveEntryResultNumber);
               positiveEntryResultNumber=findViewById(R.id.PositiveEntryResultNumber);
               positivePrevelenceNumber=findViewById(R.id.positiveTestNumbers);
               negavtivePrevelenceNumber=findViewById(R.id.negativeTestNumbers);

               VcuCase vcuCase=gson.fromJson(response,VcuCase.class);
               int studentSize =vcuCase.getStudents().size();
               Data dataStudent=vcuCase.getStudents().get(studentSize-1);
               String x1=Integer.toString(dataStudent.getValue());
               activeStudentCaseNumber.setText(x1);
               studentCase=(int) dataStudent.getValue();

               int employeeSize=vcuCase.getEmployees().size();
               Data dataEmployee=vcuCase.getEmployees().get(employeeSize-1);
               String x2=Integer.toString(dataEmployee.getValue());
               activeStudentCaseNumber.setText(x2);
               employeeCase=(int) dataEmployee.getValue();

               int isoSize=vcuCase.getIsolations().size();
               Data dataIsolation=vcuCase.getIsolations().get(isoSize-1);
               String x3=Integer.toString(dataIsolation.getValue());
               isolationNumber.setText(x3);
               isoNumber=(int) dataIsolation.getValue();

               int quaSize=vcuCase.getQuarantines().size();
               Data dataQuarantine=vcuCase.getQuarantines().get(quaSize-1);
               String x4=Integer.toString(dataIsolation.getValue());
               quarantineNumber.setText(x4);
               quaNumber=(int) dataQuarantine.getValue();

               int negEntrySize=vcuCase.getNegatives().size();
               Data dataNegEntry=vcuCase.getNegatives().get(negEntrySize-1);
               String x5=Integer.toString(dataNegEntry.getValue());
               negativeEntryResultNumber.setText(x4);
               negativeEntryNum=(int) dataNegEntry.getValue();

               int posEntrySize=vcuCase.getPositives().size();
               Data dataPosEntry=vcuCase.getPositives().get(posEntrySize-1);
               String x6=Integer.toString(dataPosEntry.getValue());
               positiveEntryResultNumber.setText(x6);
               positiveEntryNum=(int) dataPosEntry.getValue();

               int posPreveSize=vcuCase.getPrevalencePositive().size();
               Data dataPosPreve=vcuCase.getPrevalencePositive().get(posPreveSize-1);
               String x7=Integer.toString(dataPosPreve.getValue());
               positivePrevelenceNumber.setText(x7);
               posPrevelenceNum=(int) dataPosPreve.getValue();

               int negPreveSize=vcuCase.getPrevalenceNegative().size();
               Data dataNegPreve=vcuCase.getPrevalenceNegative().get(negPreveSize-1);
               String x8=Integer.toString(dataNegPreve.getValue());
               negavtivePrevelenceNumber.setText(x8);
               negPrevelenceNum=(int) dataNegPreve.getValue();



               ValueAnimator valueAnimator = ValueAnimator.ofInt(0, studentCase);
               valueAnimator.setDuration(3000);
               valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
               @Override
               public void onAnimationUpdate(ValueAnimator valueAnimator) {
                activeStudentCaseNumber.setText(valueAnimator.getAnimatedValue().toString());
               }
               });
               valueAnimator.start();

               ValueAnimator valueAnimator2 = ValueAnimator.ofInt(0, employeeCase);
               valueAnimator2.setDuration(3000);
               valueAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                   @Override
                   public void onAnimationUpdate(ValueAnimator valueAnimator) {
                       activeEmployeeCaseNumber.setText(valueAnimator.getAnimatedValue().toString());
                   }
               });
               valueAnimator2.start();

               ValueAnimator valueAnimator3 = ValueAnimator.ofInt(0, isoNumber);
               valueAnimator3.setDuration(3000);
               valueAnimator3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                   @Override
                   public void onAnimationUpdate(ValueAnimator valueAnimator) {
                       isolationNumber.setText(valueAnimator.getAnimatedValue().toString());
                   }
               });
               valueAnimator3.start();

               ValueAnimator valueAnimator4 = ValueAnimator.ofInt(0, quaNumber);
               valueAnimator4.setDuration(3000);
               valueAnimator4.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                   @Override
                   public void onAnimationUpdate(ValueAnimator valueAnimator) {
                       quarantineNumber.setText(valueAnimator.getAnimatedValue().toString());
                   }
               });
               valueAnimator4.start();

               ValueAnimator valueAnimator5 = ValueAnimator.ofInt(0, negativeEntryNum);
               valueAnimator5.setDuration(3000);
               valueAnimator5.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                   @Override
                   public void onAnimationUpdate(ValueAnimator valueAnimator) {
                       negativeEntryResultNumber.setText(valueAnimator.getAnimatedValue().toString());
                   }
               });
               valueAnimator5.start();

               ValueAnimator valueAnimator6 = ValueAnimator.ofInt(0, positiveEntryNum);
               valueAnimator6.setDuration(3000);
               valueAnimator6.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                   @Override
                   public void onAnimationUpdate(ValueAnimator valueAnimator) {
                       positiveEntryResultNumber.setText(valueAnimator.getAnimatedValue().toString());
                   }
               });
               valueAnimator6.start();

               ValueAnimator valueAnimator7 = ValueAnimator.ofInt(0, posPrevelenceNum);
               valueAnimator7.setDuration(3000);
               valueAnimator7.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                   @Override
                   public void onAnimationUpdate(ValueAnimator valueAnimator) {
                       positivePrevelenceNumber.setText(valueAnimator.getAnimatedValue().toString());
                   }
               });
               valueAnimator7.start();

               ValueAnimator valueAnimator8 = ValueAnimator.ofInt(0, negPrevelenceNum);
               valueAnimator8.setDuration(3000);
               valueAnimator8.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                   @Override
                   public void onAnimationUpdate(ValueAnimator valueAnimator) {
                      negavtivePrevelenceNumber.setText(valueAnimator.getAnimatedValue().toString());
                   }
               });
               valueAnimator8.start();



           }
       }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {
               error.printStackTrace();
           }
       });
       RequestQueue queue= Volley.newRequestQueue(this);
       queue.add(request);
       queue.start();


   }



}