package com.bignerdranch.android.geoquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String ALREADY_ANSWERED = "already_answered";
    private static final String ANSWER_COUNT = "answer_count";
    private static final String CORRECT_ANSWERS = "correct_answers" ;
    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private TextView mQuestionTextView;
    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };
    private boolean[] isAnswered = new boolean[mQuestionBank.length];
    private int mCurrentIndex = 0;
    private int mAnsweredCount = 0;
    private int mCorrectAnswers = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"OnCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);

        if(savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX,0);
            isAnswered = savedInstanceState.getBooleanArray(ALREADY_ANSWERED);
            mAnsweredCount = savedInstanceState.getInt(ANSWER_COUNT,0);
            mCorrectAnswers = savedInstanceState.getInt(CORRECT_ANSWERS,0);
        }
        mQuestionTextView = findViewById(R.id.question_text_view);
        mNextButton = findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mAnsweredCount == mQuestionBank.length){
                  showQuizResult();
                } else
                    mCurrentIndex = (mCurrentIndex + 1)%mQuestionBank.length;
                updateQuestion();
            }
        });

        mTrueButton = findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
                setButtonsDisable();
            }
        });
        mFalseButton = findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
                setButtonsDisable();
            }
        });
        updateQuestion();
    }
    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG,"onStart() called");
    }
    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG,"onResume() called");
    }
    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG,"onPause() called");
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG,"onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX,mCurrentIndex);
        savedInstanceState.putBooleanArray(ALREADY_ANSWERED,isAnswered);
        savedInstanceState.putInt(ANSWER_COUNT,mAnsweredCount);
        savedInstanceState.putInt(CORRECT_ANSWERS,mCorrectAnswers);
    }
    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG,"onStop() called");
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG,"onDestroy() called");
    }
    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
        if(isAnswered[mCurrentIndex])
            setButtonsDisable();
        else
            setButtonsEnable();
    }
    private void setButtonsEnable(){
        mTrueButton.setEnabled(true);
        mFalseButton.setEnabled(true);
    }
    private void setButtonsDisable(){
        mTrueButton.setEnabled(false);
        mFalseButton.setEnabled(false);
    }
    private void showQuizResult(){
        int score = mCorrectAnswers * 100 / mQuestionBank.length;
        Toast.makeText(this, "Quiz Finished, your score " + score + "% correct!", Toast.LENGTH_SHORT).show();
    }
    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId;
        if (userPressedTrue == answerIsTrue) {
            messageResId = R.string.correct_toast;
            mCorrectAnswers++;
        } else {
            messageResId = R.string.incorrect_toast;
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
        isAnswered[mCurrentIndex] = true;
        mAnsweredCount++;
    }
}
