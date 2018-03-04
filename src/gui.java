import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by banafshbts on 18. 3. 2.
 */
public class gui {
    private JPanel panel1;
    private JTextField req1;
    private JButton findButton;
    private JTextArea res1;
    static Vector<Vector<String >> db;
    static Map<Character,Integer> index;
    gui(){

        JFrame fram = new JFrame("spellChecker");
        fram.setContentPane(panel1);
        fram.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fram.pack();
        fram.setVisible(true);

        Vector<Character> v =new Vector<Character>();
        InputStream in = getClass().getResourceAsStream("./alphabet");
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;

        try {
            while ((line = reader.readLine()) != null) {
                int x = line.charAt(0);
                v.add(line.charAt(0));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        index =new HashMap<Character,Integer>();//index each alphabet
        Set<Character> unique = new HashSet<Character>();
        unique.addAll(v);
        int ind=0;
        for (Character  i : unique) {
            index.put(i,ind);
            ind++;
        }
        //System.out.print(index.get('ی'));
        db = new Vector<Vector<String>>();
        for(int i=0;i<ind;i++){

            Vector<String> x = new Vector<String>();
            db.add(x);

        }
        InputStream in2 = getClass().getResourceAsStream("./worddb.csv");
        BufferedReader reader2 = new BufferedReader(new InputStreamReader(in2));
        String line2;

        try {
            while ((line2 = reader2.readLine()) != null) {
                if(line2.length()>0){
                    if(index.get(line2.charAt(0))!=null) {
                        int x = index.get(line2.charAt(0));
                        db.get(x).add(line2);
                        if(line2.contains(" "))
                            db.get(x).add(line2.replace(" ","").replace("\u200C",""));
                    } else {
                        System.out.print(line2.charAt(0));
                    }
                    //System.out.print(x);
                }
                else{
                    System.out.print("this:"+line2+"\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        findButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                find();
            }
        });
    }
    public void find(){
        res1.setText("");
        String req = req1.getText();
        if(req.contains(" "))
            req=req.replace(" ","");
        Vector <String> prm = new Vector<String >();
        permutation("",req,prm);
        for(int j=0;j<prm.size();j++) {
            int x = index.get(prm.get(j).charAt(0));
            for (int i = 0; i < db.get(x).size(); i++) {

                if (db.get(x).get(i).equals(prm.get(j))) {

                    res1.append(prm.get(j)+"\n");

                }

            }
        }

    }
    private static void permutation(String prefix, String str,Vector <String> prm) {
        int n = str.length();
        if (n == 0) prm.add(prefix);
        else {
            for (int i = 0; i < n; i++)
                permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i+1, n),prm);
        }
    }
}
