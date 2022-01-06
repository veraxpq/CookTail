package edu.neu.myapplication.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

import edu.neu.myapplication.ReplacerActivity;
import edu.neu.myapplication.data.CookTest;
import edu.neu.myapplication.R;

public class LevelTestFragment extends Fragment {



    TextView question,count;
    RadioButton answer1,answer2,answer3,answer4;
//    RadioButton answerSelected;
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

    public LevelTestFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_leveltest, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);
        clickListener(view);

    }

    private void init(View view) {
        answer1 = view.findViewById(R.id.answer_btn1);
        answer2 = view.findViewById(R.id.answer_btn2);
        answer3 = view.findViewById(R.id.answer_btn3);
        answer4 = view.findViewById(R.id.answer_btn4);
        question = view.findViewById(R.id.questionText);
        nextButton=view.findViewById(R.id.nextQuestionBtn);
        answerGroup = view.findViewById(R.id.answerGroup);

        count = view.findViewById(R.id.questionNumTV);
        questionCountTotal = mCookTest.mQuestions.length;
        showNextQuestion();

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
    }

    private void clickListener(View view) {
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!answered){
                    if(answer1.isChecked() || answer2.isChecked()|| answer3.isChecked() || answer4.isChecked() ){
                        checkAnswer(view);
                    }else{
//                        Toast.makeText(activity_quiztest.this,"Please select an answer", Toast.LENGTH_SHORT).show();
                        Toast.makeText(getContext(), "Please select an answer", Toast.LENGTH_SHORT).show();
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

    private void checkAnswer(View view){
        RadioButton answerSelected = view.findViewById(answerGroup.getCheckedRadioButtonId());

        answered = true;
        String correctAnswer = mCookTest.getCorrectAnswer(questionCounter-2);
        String ansSelected = answerSelected.getText().toString();
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

    private void updateQuestion(int num){
        question.setText(mCookTest.getQuestion(num));
        answer1.setText(mCookTest.getChoice1(num));
        answer2.setText(mCookTest.getChoice2(num));
        answer3.setText(mCookTest.getChoice3(num));
        answer4.setText(mCookTest.getChoice4(num));
        mAnswer = mCookTest.getCorrectAnswer(num);
    }

    private void finishQuiz(){

        if(mScore < 2){
            level = 1;
        }else if(mScore <4){
            level =2;
        }else {
            level =3;
        }

        DocumentReference userRef = FirebaseFirestore.getInstance().collection("Users").document(user.getUid());
        Map<String, Object> data = new HashMap<>();
        data.put("level", level);
        userRef.set(data, SetOptions.merge());

//        ((ReplacerActivity)getActivity()).setFragment(new LearningMainFragment());
//        Intent intent = new Intent(activity_quiztest.this, LevelActivity.class);
//        intent.putExtra("level",level);
//        startActivity(intent);
    }



}