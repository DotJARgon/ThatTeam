package user_services;
import java.security.*;
public class ResetPassToken {
    private String question1;
    private String question2;
    private String question3;
    private String answer1;
    private String answer2;
    private String answer3;
    private String username;
    private String password;
    private String newPassword;
    
    public ResetPassToken(String username, String password, String question1, String question2, String question3, String answer1, String answer2, String answer3) {
        this.username = username;
        this.password = md5(password, username);
        this.question1 = question1;
        this.question2 = question2;
        this.question3 = question3;
        this.answer1 = md5(answer1, this.question1);
        this.answer2 = md5(answer2, this.question2);
        this.answer3 = md5(answer3, this.question3);
    }

    private String md5(String ptxt, String salt) { //salt is meant to be the username
        String ptxtSalt = ptxt + salt; // concatenate the password and salt
        String result = null; // initialize the generated password
        try {  //code is from https://www.mkyong.com/java/java-md5-hashing-example/, thinking of using a different hashing algorithm, like sha256
          MessageDigest md = MessageDigest.getInstance("MD5"); 
          md.update(ptxtSalt.getBytes());
          byte[] bytes = md.digest();
          StringBuilder sb = new StringBuilder();
          for (int i = 0; i < bytes.length; i++) {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
          }
          result = sb.toString();
        } catch (NoSuchAlgorithmException e) {
          e.printStackTrace();
        }
        return result;
    }

    public String getQuestion1() {
        return question1;
    }

    public String getQuestion2() {
        return question2;
    }

    public String getQuestion3() {
        return question3;
    }

    public boolean matchAnswer(String answer, String question) {
        if (question.equals(question1)) {
            return answer.equals(md5(answer1, question1));
        } else if (question.equals(question2)) {
            return answer.equals(md5(answer2, question2));
        } else if (question.equals(question3)) {
            return answer.equals(md5(answer3, question3));
        } else {
            return false;
        }
    }

}
