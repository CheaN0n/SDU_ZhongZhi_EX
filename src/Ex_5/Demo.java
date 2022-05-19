package Ex_5;

import domain.Votes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


public class Demo {
    static final int MAX_M = 100;
    static final int MAX_N = 100;
    public static Votes readFromFile()  {
        System.out.println("请输入存储投票情况的文件名称(文件默认存储在input目录下)");
        Scanner scanner = new Scanner(System.in);
        String FileName = scanner.nextLine();
        Votes votes = null;
        try {
            BufferedReader in = new BufferedReader(new FileReader("src/EX_5/input/"+FileName));
            String [][]temp_Votes = new String[MAX_M][MAX_N];
            int m_count = 0;
            String personalVotes = null;
            while((personalVotes = in.readLine())!=null){
                String []strs_personalVotes = personalVotes.split(" ");
                temp_Votes[m_count++] = strs_personalVotes;
            }
            int n_count = temp_Votes[0].length;
            votes = new Votes(m_count,n_count);
            votes.inputMatrx(temp_Votes);
        }catch (IOException e){
            System.err.println("文件读取失败");
        }
        return votes;
    }



    public static void main(String[] args) {
        Votes votes = readFromFile();
        votes.statistical();

    }
}
