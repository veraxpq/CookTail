package edu.neu.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

import edu.neu.myapplication.data.CookTest;

public class LevelTestActivity extends AppCompatActivity {

    TextView question,score,count;
    RadioButton answer1,answer2,answer3,answer4;
    Button nextButton;
    private CookTest mCookTest = new CookTest();

    private String mAnswer;
    private int mScore = 0;
    private RadioGroup answerGroup;
    private int questionCounter = 1;
    private int questionCountTotal;
    private boolean answered;
    private int level;

    FirebaseUser user;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_test);

        answer1 = (RadioButton) findViewById(R.id.answer_btn1);
        answer2 = (RadioButton)findViewById(R.id.answer_btn2);
        answer3 = (RadioButton)findViewById(R.id.answer_btn3);
        answer4 = (RadioButton)findViewById(R.id.answer_btn4);

        nextButton=(Button)findViewById(R.id.nextQuestionBtn);
        question = (TextView) findViewById(R.id.questionText);

        count = (TextView)findViewById(R.id.questionNumTV);
        answerGroup = (RadioGroup) findViewById(R.id.answerGroup);
        questionCountTotal = mCookTest.mQuestions.length;

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        showNextQuestion();

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!answered){
                    if(answer1.isChecked() || answer2.isChecked()|| answer3.isChecked() || answer4.isChecked() ){
                        checkAnswer();
                    }else{
                        Toast.makeText(LevelTestActivity.this,"Please select an answer", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    showNextQuestion();
                }
            }
        });
    }

    private void showNextQuestion(){
        //clear check
        answerGroup.clearCheck();
        if(questionCounter <= questionCountTotal){
            updateQuestion(questionCounter-1);
            count.setText(questionCounter + " / " +questionCountTotal);
            questionCounter++;
            answered = false;
            nextButton.setText("Confirm");
        }else{
            finishQuiz();
        }
    }

    private void checkAnswer(){
        answered = true;

        RadioButton answerSelected = findViewById(answerGroup.getCheckedRadioButtonId());
        String correctAnswer = mCookTest.getCorrectAnswer(questionCounter-2);
        String ansSelected = (String) answerSelected.getText();
        if(ansSelected == correctAnswer){
            mScore ++;
        }
//        score.setText("Score: "+mScore);

        int num = questionCounter-1;
        if(num < questionCountTotal){
            nextButton.setText("Next");
        }else{
            nextButton.setText("Submit");
        }
    }

    private void finishQuiz(){
        finish();
        if(mScore < 2){
            level = 1;
        }else if(mScore <4){
            level =2 ;
        }else {
            level =3;
        }

        DocumentReference userRef = FirebaseFirestore.getInstance().collection("Users").document(user.getUid());
        Map<String, Object> data = new HashMap<>();
        data.put("level", level);
        data.put("tested", true);
        userRef.set(data, SetOptions.merge());

        Intent intent = new Intent(LevelTestActivity.this, MainActivity.class);
        intent.putExtra("id",1);
        startActivity(intent);

//        Intent intent = new Intent(activity_quiztest.this, LevelActivity.class);
//        intent.putExtra("level",level);
//        startActivity(intent);
    }
    private void updateQuestion(int num){
        question.setText(mCookTest.getQuestion(num));
        answer1.setText(mCookTest.getChoice1(num));
        answer2.setText(mCookTest.getChoice2(num));
        answer3.setText(mCookTest.getChoice3(num));
        answer4.setText(mCookTest.getChoice4(num));
        mAnswer = mCookTest.getCorrectAnswer(num);
    }

}