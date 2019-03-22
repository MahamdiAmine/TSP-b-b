import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class test4 {

    String message = "Robert";

    @Test
    public void testSalutationMessage() {
        System.out.println("Inside testSalutationMessage()");
        message = "Hi!" + "Robert";
        assertEquals(message,"ibi");
    }
}