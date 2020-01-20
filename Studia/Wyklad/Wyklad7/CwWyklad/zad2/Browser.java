import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URL;

import javax.swing.*;

public class Browser extends JFrame implements ActionListener {
    private static final String COMMAND_GO = "go";

    private JEditorPane webpage;
    private JTextField url;
    private JTextArea htmlPage;

    private JPanel createMainPanel() {
        final JPanel mp = new JPanel();
        // panel z kolumnowym (Y_AXIS) ukladem elementow
        mp.setLayout(new BoxLayout(mp, BoxLayout.Y_AXIS));
        // panel z domyslnym FlowLayout
        final JPanel p = new JPanel();
        this.url = new JTextField();

        // sugerowane rozmiary moga zostac zmienione przez layout managera
        this.url.setPreferredSize(new Dimension(500, 20));
        this.url.setText("http://www.simongrant.org/web/guide.html");
        final JLabel l = new JLabel("adres");
        // label opisujacy url
        l.setLabelFor(this.url);

        // dodajemy do panelu JLabel i JTextFieeld
        p.add(l);
        p.add(this.url);

        // tworzymy przycisk
        final JButton b = new JButton("Go");
        b.setActionCommand(COMMAND_GO);

        b.addActionListener(this);
        b.setPreferredSize(new Dimension(100, 40));
        // dodanie przycisku do panelu
        p.add(b);

        // dodanie panelu p (z komponentami rozmieszczonymi przez FlowLayout) do panelu,
        // w ktorym obowiazuje BoxLayout
        mp.add(p);

        this.webpage = new JEditorPane();
        this.htmlPage = new JTextArea();

        try {
            // wczytujamy zawartosc ze strony
            this.setPage(new URL("http://www.simongrant.org/web/guide.html"));
        } catch (final IOException e) {
        }
        // Tworzenie panelu z zakladkami
        final JTabbedPane tp = new JTabbedPane();
        tp.setPreferredSize(new Dimension(600, 400));

        // pole tekstowe webpage umieszczamy wewnatrz panela
        // scrollowanego. Dzieki temu zawartosc okienka bedzie mogla
        // zajmowac wiecej miejsca niz widok

        JScrollPane sp = new JScrollPane(this.webpage);
        // zakladka “page” bedzie zawierac webpage (wewnatrz JScrollPane)
        tp.add("page", sp);

        // zakladka “page” bedzie zawierac webpage (wewnatrz JScrollPane)
        sp = new JScrollPane(this.htmlPage);
        tp.add("html", sp);

        // przygotowany JTabbedPane zostaje dodany do panelu mp
        mp.add(tp);

        return mp;
    }

    private void setPage(final URL page) throws IOException {
        String s;
        this.webpage.setPage(page);
        final BufferedReader br = new BufferedReader(new InputStreamReader(page.openStream()));

        while ((s = br.readLine()) != null)
            this.htmlPage.append(s + "\n");
    }

    public Browser() {
        super();
        this.getContentPane().add(this.createMainPanel());
    }

    public static void createAndShow() {
        final Browser b = new Browser();
        b.setDefaultCloseOperation(EXIT_ON_CLOSE);
        b.pack();
        b.setLocationRelativeTo(null);
        b.setVisible(true);
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        if(COMMAND_GO.equals(e.getActionCommand())) {
            try {
                //przeladuj strone
                this.setPage(new URL(this.url.getText()));
            } catch(final IOException e2) {
                this.webpage.setText("Problem z adresem " + this.url.getText());
                this.htmlPage.setText("Problem z adresem " + this.url.getText());
            }
        }
    }

    public static void main(final String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShow();
            }
        });
    }
}