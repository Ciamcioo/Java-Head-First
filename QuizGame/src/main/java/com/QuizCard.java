package main.java.com;

/**
 * QuizCard
 */
public class QuizCard {
    private String question, answear;

    public QuizCard(String question, String answear) {
        this.question = question;
        this.answear = answear;
    }
    
    public String getQuestion() {
        return question;
    }
    
    public String getAnswear() {
        return answear;
    }
    
}
