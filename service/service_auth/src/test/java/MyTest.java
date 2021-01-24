import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class MyTest {
    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("123456");
        System.out.print(encode);
    }
}
