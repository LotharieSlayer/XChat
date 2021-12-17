import java.io.BufferedReader;
import java.io.PrintWriter;

public class Client {
    private final BufferedReader in;
    private final PrintWriter out;

    /**
     * Constructeur de Client
     * @param in
     * @param out
     */
    public Client(BufferedReader in, PrintWriter out){
        this.in = in;
        this.out = out;
    }

    /**
     * Get le BufferedReader
     * @return BufferedReader
     */
    public BufferedReader getIn() {
        return in;
    }

    /**
     * Get le PrintWriter
     * @return PrintWriter
     */
    public PrintWriter getOut() {
        return out;
    }
}
