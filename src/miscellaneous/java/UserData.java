package miscellaneous.java;

public class UserData {
    public static String userEmail;
//    private String userPassword;
//    private String userFirstName;
    private static String userLastName;
//    private String userPhoneNumber;
    private static String userGender;
//    private String userNid_BReg;
//    private String userPassport;
//    private String userImage;
    private int i,j;

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public UserData() {
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }
}
