import com.dragonpass.global.utils.Aes256Utils;
import org.junit.Test;

public class Aes256UtilTest {
  @Test
  public void aes256(){
    //529918xxxxxx0161
    //542057XXXX1627
    //5371 78** **** 2909
    //552296XXXX3243
//    552489xxxx1452
    //552489xxxxxx4854
//    529918xxxxxx8589
    //524236******8353
    String s = Aes256Utils.encryptWithDes3Secret("denises@wavedirect.net", "5En7QaV2mCdv06eIn1wVrA==");
    String s2 = Aes256Utils.encryptWithDes3Secret("9433", "5En7QaV2mCdv06eIn1wVrA==");
    System.out.println(s+"--------"+s2);
    System.out.println(Aes256Utils.decryptWithDes3Secret("6522cbf635dd0a5cb4c19c5f760799603f41196d58fcef408b61f7e856356889", "5En7QaV2mCdv06eIn1wVrA=="));
    System.out.println(Aes256Utils.decryptWithDes3Secret("ee478b8c761b0417f23043cd6608a8943076ace59a3cf60bab6698b87dcad66c", "5En7QaV2mCdv06eIn1wVrA=="));
  }

  @Test
  public void aes256s(){
    String[] emails={"superskiy@gmail.com"};
    for (String email:emails){
      //万事达欧洲
      System.out.println(Aes256Utils.encryptWithDes3Secret(email, "QR1+NukkwxWY32gfnw6SFi/h3JrqE+Sa"));
    }

    String des3Secret = Aes256Utils.decryptWithDes3Secret(
        "a43db5bbbf576b293d264e3b4b445070404e36d1f4ad09e5d83fa0234caf8aa5",
        "QR1+NukkwxWY32gfnw6SFi/h3JrqE+Sa");
    System.out.println(des3Secret);
  }

  /**
   * dev 环境
   */
  @Test
  public void aes256eu(){

    String des3Secret = Aes256Utils.decryptWithDes3Secret(
        "169e5dc0432a7ace29a97be23a0d1393c9b9c4da8ed1baf3ecd01d223e72bf6e",
        "QR1+NukkwxWY32gfnw6SFuvx8s/a+L9w");
    System.out.println(des3Secret);
  }
  /**
   * uat 环境
   */
  @Test
  public void aes256euUat(){

    String des3Secret = Aes256Utils.decryptWithDes3Secret(
        "6d50a86bcb9ad20afa0565705a2280c7",
        "QR1+NukkwxWY32gfnw6SFuDIbusCaOWm");
    System.out.println(des3Secret);
    des3Secret = Aes256Utils.encryptWithDes3Secret(
        "a39d1b2011ca0ea04c18d7bcb8f4c137c9f175441711ceb1db2ca97a605a106a9d15e9b5125196c95fd174f5a4d80b952dc62797e9d732f8de02e2d806733605",
        "QR1+NukkwxWY32gfnw6SFuDIbusCaOWm");
    System.out.println(des3Secret);
  }

}
