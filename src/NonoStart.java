import java.io.IOException;

public class NonoStart
{
    public static final void main(String[] args) throws IOException
    {
        NonoFrame frame = new NonoFrame(100, 50, 800, 500);
        frame.setTitle("Nonograms");
        NonoPanel panel = new NonoPanel(frame);
        frame.setPanel(panel);
        frame.add(panel);
        frame.getPanel().setVisible(true);
    }
}
