package edu.neu.myapplication.data;

public class CookTest {

    public String mQuestions[] ={
            "How long have you been cooking?",
            "How does adding salt to boiling water affect it?",
            "Which of these cutting boards is the most sanitary?",
            "How many tablespoons are in a cup",
            "The sauce you are using has become too watery, what is the best way to thicken it?"
    };

    private String mChoices[][]={
            {"Just Started","Less than 1 years","1-2 years","More than 2 years"},
            {"Speeds up the process of boiling water.", "It delays the process of water boiling.", "Softens the water.", "Stops the water from boiling at all."},
            {"Wood", "Bamboo", "Plastic", "All above"},
            {"8","14","12","16"},
            {"Add Flour or Corn-starch.", "Put it in the freezer.", "Add Sauce.", "Add butter and olive oil."},

    };

    private String mCorrectAnswer[] ={
            "More than 2 years",
            "It delays the process of water boiling.",
            "Plastic",
            "16",
            "Add Flour or Corn-starch."
    };

    public String getQuestion(int a){
        String question = mQuestions[a];
        return question;
    }

    public String getChoice1 (int a){
        String choice = mChoices[a][0];
        return choice;
    }

    public String getChoice2 (int a){
        String choice = mChoices[a][1];
        return choice;
    }
    public String getChoice3 (int a){
        String choice = mChoices[a][2];
        return choice;
    }
    public String getChoice4 (int a){
        String choice = mChoices[a][3];
        return choice;
    }

    public String getCorrectAnswer(int a){
        String answer = mCorrectAnswer[a];
        return answer;
    }
}
