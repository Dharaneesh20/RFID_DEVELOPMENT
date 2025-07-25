import org.mindrot.jbcrypt.BCrypt;
public class BCryptHash {
    public static void main(String[] args) {
        System.out.println(BCrypt.hashpw("rsdh", BCrypt.gensalt()));
    }
}
