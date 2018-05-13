import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class lab2 {
    static String alphabet = "abcdefghijklmnopqrstuvwxyz";
    int series = 0;

    static public String getRandomString() {
        return "" + alphabet.charAt(new Random().nextInt(26)) + alphabet.charAt(new Random().nextInt(26)) + alphabet.charAt(new Random().nextInt(26));
    }

    public Random random = new Random();

    class container {
        int int1;
        int int2;
        int int3;
        String str4;
        String str5;

        container(int fistInt, int secondInt, int thirdInt, String fourStr, String fiveStr) {
            this.int1 = fistInt;
            this.int2 = secondInt;
            this.int3 = thirdInt;
            this.str4 = fourStr;
            this.str5 = fiveStr;
        }

        container() {
        }

        container(container c) {
            this.int1 = c.int1;
            this.int2 = c.int2;
            this.int3 = c.int3;
            this.str4 = c.str4;
            this.str5 = c.str5;
        }

        container(String[] ar) {
            this.int1 = Integer.parseInt(ar[0]);
            this.int2 = Integer.parseInt(ar[1]);
            this.int3 = Integer.parseInt(ar[2]);
            this.str4 = ar[3];
            this.str5 = ar[4];
        }


    }

    File fileOut = new File("output");
    File fileSort = new File("postPreSort");
    File fileCopyPostSort = new File("copyPostPreSort");
    File fileIn = new File("input");
    ArrayList<container> list = new ArrayList<container>();
    int n = 0, nVsego = 0, nSchet;
    Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        new lab2();
    }

    public lab2() {
        fileIn.delete();
        fileSort.delete();
        fileOut.delete();
        System.out.println("Введите количество элементов");
        nVsego = in.nextInt();
        System.out.println("Введите по какому полю сортировать(1-6, 1-3 Int,4-5 string)");
        int f = in.nextInt();
        System.out.println("Введите по какому полю сортировать(1-6, 1-3 Int,4-5 string)");
        int s = in.nextInt();
        System.out.println("Введите по какому полю сортировать(1-6, 1-3 Int,4-5 string)");
        int t = in.nextInt();

        int allRecord=0;
        while (allRecord < nSchet) {
            int rand_count=random.nextInt()%1000;

            list = createList(rand_count, rand_count);
            saveInFile(list, fileIn, true);
            saveInFile("-",fileIn,true);
            ArrayList<container> res = sort(f, s, t, list);
            saveInFile(res, fileSort, true);
            saveInFile("-",fileSort,true);
            allRecord += rand_count;
        }

        //start work
        copy(fileSort, fileCopyPostSort, false);
        //work with fileSort
        //File fl = sort(f, s, t, fileSort);
        split(new int[]{f,s,t},fileSort);
        File output = new File("output");
        //copy(fl, output, false);



    }

    int StrInFile(File file)
    {
        int count=0;
        String str;
        try {
            FileReader fr=new FileReader(file);
            BufferedReader br=new BufferedReader(fr);
            while((str=br.readLine())!=null)
            {
                if(str.equals("-"))count++;
            }
            return count;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }

    void split(int[] fields,File file) {
        File main= file;

            int max_blocks;
            int cbf ; //количество блоков в первом файле
            int cbs = 0; //количество блоков во втором файле

            do {

                //подсчёт блоков в файле
                max_blocks = StrInFile(file);

                //разделение на два файла
                if (max_blocks > 1) {

                    cbf = max_blocks / 2;
                    cbs = max_blocks - cbf;

                    //переписываем блоки в первый файл
                    block_recording(main, "file1.txt", cbf);

                    //переписываем блоки во второй файл
                    block_recording(main, "file2.txt", cbs);

                }

                if (max_blocks > 1) merge(fields, cbs);

            } while (max_blocks > 1);
    }
    void merge(int[] fields, int cab) {

        ofstream bofs;
        bofs.open("buffer_file.txt");

        ifstream ifile_f; ifstream ifile_s;
        ifile_f.open("file1.txt");
        ifile_s.open("file2.txt");

        int cab_counter = 0;

        string str_f; getline(ifile_f, str_f);
        string str_s; getline(ifile_s, str_s);

        do {
            //если 1 строка равна порогу, заносим строки 2 файла в основной, пока не дойдем до порога
            if (str_f == "-1 -1 -1 *** ***") {
                while (str_s != "-1 -1 -1 *** ***") {
                    bofs << str_s << endl;
                    getline(ifile_s, str_s);
                }
                cab_counter++;

                if(cab_counter != cab)
                    bofs << "-1 -1 -1 *** ***" << endl;
                else
                    bofs << "-1 -1 -1 *** ***" ;

                getline(ifile_f, str_f);
                getline(ifile_s, str_s);
            }
            else
                //если 2 строка равна порогу, заносим строки 1 файла в основной, пока не дойдем до порога
                if (str_s == "-1 -1 -1 *** ***") {
                    while (str_f != "-1 -1 -1 *** ***") {
                        bofs << str_f << endl;
                        getline(ifile_f, str_f);
                    }
                    cab_counter++;

                    if (cab_counter != cab)
                        bofs << "-1 -1 -1 *** ***" << endl;
                    else
                        bofs << "-1 -1 -1 *** ***";

                    getline(ifile_f, str_f);
                    getline(ifile_s, str_s);
                }
                //сравниваем 2 строки
                else {

                    vector<string> list_f;
                    str_split(&list_f, str_f);
                    _element *elF = new _element(atoi(list_f[0].c_str()), atoi(list_f[1].c_str()), atoi(list_f[2].c_str()), list_f[3], list_f[4]);
                    list_f.clear();

                    vector<string> list_s;
                    str_split(&list_s, str_s);
                    _element *elS = new _element(atoi(list_s[0].c_str()), atoi(list_s[1].c_str()), atoi(list_s[2].c_str()), list_s[3], list_s[4]);
                    list_s.clear();


                    if (elF->compare_all_element(elS, fields)) {
                        bofs << str_f << endl;
                        getline(ifile_f, str_f);
                    }

                    else {
                        bofs << str_s << endl;
                        getline(ifile_s, str_s);
                    }

                    delete elF; delete elS;
                }

        } while (!ifile_f.eof() || !ifile_s.eof());


        ifile_f.close();
        ifile_s.close();
        bofs.close();
        remove("file1.txt");
        remove("file2.txt");

    }

    /*функция записи блоков в буфферный файл*/
    void block_recording(File main, String name, int border) {
        try {
            String str;
            FileWriter ofs=new FileWriter(new File(name));
            FileReader fr=new FileReader(main);
            BufferedReader bf=new BufferedReader(fr);
            for (int i = 0; i < border;) {
                str=bf.readLine();
                if (i == border - 1)
                    if (str .equals("-")) {
                        ofs.write(str);
                        i++;
                    }
                    else
                        ofs.write(str+"\n");
                else
                if (str.equals("-")) {
                    ofs.write(str+"\n");
                    i++;
                }
                else
                    ofs.write(str+"\n");
            }
            ofs.flush();
            ofs.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    ArrayList<container> createList(int count, int predel) {
        ArrayList<container> result = new ArrayList<container>();
        for (int i = 0; i < count; i++) {
            container con = new container();
            con.int1 = random.nextInt(predel);
            con.int2 = random.nextInt(predel);
            con.int3 = random.nextInt(predel);
            con.str4 = getRandomString();
            con.str5 = getRandomString();
            result.add(con);
        }
        return result;
    }

    ArrayList<container> sort(int f, int s, int t, ArrayList<container> list) {
        ArrayList<container> result = new ArrayList<container>();
        ArrayList<container> mesh = new ArrayList<container>();
        quickSort(list, f);
        while (!list.isEmpty()) {
            mesh.clear();
            mesh.add(list.get(0));
            list.remove(0);
            switch (f) {
                case 1:
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).int1 == mesh.get(0).int1) {
                            mesh.add(list.get(i));
                            list.remove(i);
                            i--;
                        }
                    }
                    break;
                case 2:
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).int2 == mesh.get(0).int2) {
                            mesh.add(list.get(i));
                            list.remove(i);
                            i--;
                        }
                    }
                    break;
                case 3:
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).int3 == mesh.get(0).int3) {
                            mesh.add(list.get(i));
                            list.remove(i);
                            i--;
                        }
                    }
                    break;
                case 4:
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).str4.equals(mesh.get(0).str4)) {
                            mesh.add(list.get(i));
                            list.remove(i);
                            i--;
                        }
                    }
                    break;
                case 5:
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).str5.equals(mesh.get(0).str5)) {
                            mesh.add(list.get(i));
                            list.remove(i);
                            i--;
                        }
                    }
                    break;
            }
            result.addAll(sort(s, t, mesh));
        }
        return result;
    }

    ArrayList<container> sort(int s, int t, ArrayList<container> list) {
        ArrayList<container> result = new ArrayList<container>();
        ArrayList<container> mesh = new ArrayList<container>();
        quickSort(list, s);
        while (!list.isEmpty()) {
            mesh.clear();
            mesh.add(list.get(0));
            list.remove(0);
            switch (s) {
                case 1:
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).int1 == mesh.get(0).int1) {
                            mesh.add(list.get(i));
                            list.remove(i);
                            i--;
                        }
                    }
                    break;
                case 2:
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).int2 == mesh.get(0).int2) {
                            mesh.add(list.get(i));
                            list.remove(i);
                            i--;
                        }
                    }
                    break;
                case 3:
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).int3 == mesh.get(0).int3) {
                            mesh.add(list.get(i));
                            list.remove(i);
                            i--;
                        }
                    }
                    break;
                case 4:
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).str4.equals(mesh.get(0).str4)) {
                            mesh.add(list.get(i));
                            list.remove(i);
                            i--;
                        }
                    }
                    break;
                case 5:
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).str5.equals(mesh.get(0).str5)) {
                            mesh.add(list.get(i));
                            list.remove(i);
                            i--;
                        }
                    }
                    break;
            }
            result.addAll(sort(t, mesh));
        }
        return result;
    }

    ArrayList<container> sort(int t, ArrayList<container> list) {
        quickSort(list, t);
        return list;
    }

    void copy(File from, File in, boolean append) {
        try {
            FileReader fr = new FileReader(from);
            FileWriter fw = new FileWriter(in, append);
            BufferedReader bf = new BufferedReader(fr);
            String red;
            while ((red = bf.readLine()) != null) {
                fw.write(red);
                fw.write("\n");
            }
            fw.flush();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveInFile(ArrayList<container> list, File file, boolean append) {
        try {
            FileWriter fileWriter = new FileWriter(file, append);
            String wr = "";
            for (int i = 0; i < list.size(); i++) {
                container container = list.get(i);
                wr = "";
                wr += container.int1 + " ";
                wr += container.int2 + " ";
                wr += container.int3 + " ";
                wr += container.str4 + " ";
                wr += container.str5 + " ";
                wr += "\n";
                fileWriter.write(wr);
            }
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveInFile(String str, File file, boolean append) {
        try {
            FileWriter fileWriter = new FileWriter(file, append);
            fileWriter.write(str+"\n");
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void quickSort(ArrayList<container> arrayList, int col) {
        doSort(0, arrayList.size() - 1, arrayList, col);
    }

    private void doSort(int start, int end, ArrayList<container> array, int column) {
        if (start >= end)
            return;
        int i = start, j = end;
        int cur = i - (i - j) / 2;
        while (i < j) {

            switch (column) {
                case 1:
                    while (i < cur && (array.get(i).int1 <= array.get(cur).int1)) {
                        i++;
                    }
                    while (j > cur && (array.get(cur).int1 <= array.get(j).int1)) {
                        j--;
                    }
                    break;
                case 2:
                    while (i < cur && (array.get(i).int2 <= array.get(cur).int2)) {
                        i++;
                    }
                    while (j > cur && (array.get(cur).int2 <= array.get(j).int2)) {
                        j--;
                    }
                    break;
                case 3:
                    while (i < cur && (array.get(i).int3 <= array.get(cur).int3)) {
                        i++;
                    }
                    while (j > cur && (array.get(cur).int3 <= array.get(j).int3)) {
                        j--;
                    }
                    break;
                    /*
                case 4:
                    while (i < cur && (array.get(i).str4 <= array.get(cur).str4)) {
                        i++;
                    }
                    while (j > cur && (array.get(cur).char4 <= array.get(j).char4)) {
                        j--;
                    }
                    break;
                case 5:
                    while (i < cur && (array.get(i).char5 <= array.get(cur).char5)) {
                        i++;
                    }
                    while (j > cur && (array.get(cur).char5 <= array.get(j).char5)) {
                        j--;
                    }
                    break;
                    */
                case 4:
                    while (i < cur && (array.get(i).str4.compareTo(array.get(cur).str4) <= 0)) {
                        i++;
                    }
                    while (j > cur && (array.get(cur).str4.compareTo(array.get(j).str4) <= 0)) {
                        j--;
                    }
                    break;
                case 5:
                    while (i < cur && (array.get(i).str5.compareTo(array.get(cur).str5) <= 0)) {
                        i++;
                    }
                    while (j > cur && (array.get(cur).str5.compareTo(array.get(j).str5) <= 0)) {
                        j--;
                    }
                    break;
            }

            if (i < j) {
                Collections.swap(array, i, j);
                if (i == cur)
                    cur = j;
                else if (j == cur)
                    cur = i;
            }
        }
        doSort(start, cur, array, column);
        doSort(cur + 1, end, array, column);
    }

    boolean fileIsEmpty(File file) {
        if (file.length() == 0) return true;
        else return false;
    }

    int sravnenie(container c1, container c2, int p) {
        int res = 0;
        switch (p) {
            case 1:
                if (c1.int1 > c2.int1) res = 1;
                else if (c1.int1 < c2.int1) res = -1;
                else res = 0;
                break;
            case 2:
                if (c1.int2 > c2.int2) res = 1;
                else if (c1.int2 < c2.int2) res = -1;
                else res = 0;
                break;
            case 3:
                if (c1.int3 > c2.int3) res = 1;
                else if (c1.int3 < c2.int3) res = -1;
                else res = 0;
                break;
            case 4:
                if (p == 4) res = c1.str4.compareTo(c2.str4);
                break;
            case 5:
                if (p == 5) res = c1.str5.compareTo(c2.str5);
                break;
        }
        return res;
    }


    {
    /*
    File merge(File[] files,int p)
    {
        File res=new File("connect");

        try {
            FileReader fr1=new FileReader(files[0]);
            FileReader fr2=new FileReader(files[1]);

            BufferedReader bf1=new BufferedReader(fr1);
            BufferedReader bf2=new BufferedReader(fr2);

            FileWriter fileWriter=new FileWriter(res);

            String str1=bf1.readLine(),str2=bf2.readLine();
            while((str1!=null)&&(str2!=null))
            {
                if((str1.equals("-"))&&(str2.equals("-")))
                {
                    str1=bf1.readLine();
                    str2=bf2.readLine();
                }else if((str1.equals("-")))
                {
                    fileWriter.write(str2+"\n");
                    while(!(str2=bf2.readLine()).equals("-"))
                    {
                        fileWriter.write(str2+"\n");
                    }
                    str1=bf1.readLine();
                    str2=bf2.readLine();
                }else if((str2.equals("-")))
                {
                    fileWriter.write(str1+"\n");
                    while(!(str1=bf1.readLine()).equals("-"))
                    {
                        fileWriter.write(str1+"\n");
                    }
                    str1=bf1.readLine();
                    str2=bf2.readLine();
                }else
                {
                    container c1=new container(str1.split(" "));
                    container c2=new container(str2.split(" "));
                    if(sravnenie(c1,c2,p)<0)
                    {
                        fileWriter.write(str1+"\n");
                        str1=bf1.readLine();
                    }else
                    {
                        fileWriter.write(str2+"\n");
                        str2=bf2.readLine();
                    }
                }
                fileWriter.flush();
            }
            if(str1==null)
            {
                if(str2.equals("-"))str2=bf2.readLine();
                fileWriter.write(str2+"\n");
                while((str2=bf2.readLine())!=null)
                {
                    if(str2.equals("-"))str2=bf2.readLine();
                    fileWriter.write(str2+"\n");
                }
                fileWriter.flush();
            }else if(str2==null)
            {
                if(str1.equals("-"))str1=bf1.readLine();
                fileWriter.write(str1+"\n");
                while((str1=bf1.readLine())!=null)
                {
                    if(str1.equals("-"))str1=bf1.readLine();
                    fileWriter.write(str1+"\n");
                }
                fileWriter.flush();
            }
            return res;


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    */
    }
    {/*
        File connect (File[]files,int p)
        {
            File res = new File("connect");

            try {
                FileReader fr1 = new FileReader(files[0]);
                FileReader fr2 = new FileReader(files[1]);

                BufferedReader bf1 = new BufferedReader(fr1);
                BufferedReader bf2 = new BufferedReader(fr2);

                FileWriter fileWriter = new FileWriter(res);
                //Start merge
                String str1 = bf1.readLine(), str2 = bf2.readLine();
                while (((str1) != null) && ((str2) != null)) {
                    if ((str1.equals("-")) && (str2.equals("-"))) {
                        str1 = bf1.readLine();
                        str2 = bf2.readLine();
                    } else if (str1.equals("-")) {
                        while (str2 != null) {
                            if (str2.equals("-")) break;
                            fileWriter.write(str2 + "\n");
                            str2 = bf2.readLine();
                        }
                        fileWriter.flush();
                        if (str2 == null) {
                            while (str1 != null) {
                                if (!str1.equals("-")) fileWriter.write(str1 + "\n");
                                str1 = bf1.readLine();
                            }
                            fileWriter.flush();
                        }
                    } else if (str2.equals("-")) {
                        while (str1 != null) {
                            if (str1.equals("-"))
                                break;
                            fileWriter.write(str1 + "\n");
                            str1 = bf1.readLine();
                        }
                        fileWriter.flush();
                        if (str1 == null) {
                            while (str2 != null) {
                                if (!str2.equals("-")) fileWriter.write(str2 + "\n");
                                str2 = bf2.readLine();
                            }
                            fileWriter.flush();
                        }
                    } else {
                        container c1 = new container(str1.split(" "));
                        container c2 = new container(str2.split(" "));
                        if (sravnenie(c1, c2, p) < 0) {
                            fileWriter.write(str1 + "\n");
                            str1 = bf1.readLine();
                        } else {
                            fileWriter.write(str2 + "\n");
                            str2 = bf2.readLine();
                        }
                        fileWriter.flush();
                    }
                }
                if (str1 == null) {
                    while (str2 != null) {
                        if (str2.equals("-")) str2 = bf2.readLine();
                        fileWriter.write(str2 + "\n");
                        str2 = bf2.readLine();
                    }
                    fileWriter.flush();
                } else if (str2 == null) {
                    while (str1 != null) {
                        if (str1.equals("-")) str1 = bf1.readLine();
                        fileWriter.write(str1 + "\n");
                        str1 = bf1.readLine();
                    }
                    fileWriter.flush();
                }

                return res;


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    */}
    //Old Code
    {
    /*
        void sort(int f,int s, int t)
        {

        }

        void sort(int s, int t)
        {

        }
        void sort(int t,File ish)
        {
            try {
                File n1=new File("n1");
                File n2=new File("n2");
                boolean agen=false;
                int p,n;
                messege("Start divide");
                while (!agen)
                {
                        messege("Divide");
                        FileReader fl=new FileReader(ish);
                        FileWriter f1=new FileWriter(n1,false);
                        FileWriter f2=new FileWriter(n2,false);
                        boolean nowWr=true;
                        BufferedReader in =new BufferedReader(fl);
                        Cont last;
                        Cont now;
                        String red=in.readLine();
                        last=gson.fromJson(red,Cont.class);
                        write(true,gson.toJson(last),f1,f2);

                        while((red=in.readLine())!=null)
                        {
                            now=gson.fromJson(red,Cont.class);
                            switch (t)
                            {
                                case 1:
                                    if(now.int1>=last.int1)
                                    {
                                        write(nowWr,gson.toJson(now),f1,f2);
                                    }else
                                    {
                                        write(nowWr,"'",f1,f2);
                                        nowWr=!nowWr;
                                        write(nowWr,gson.toJson(now),f1,f2);
                                    }
                                    break;
                                case 2:
                                    if(now.int2>=last.int2)
                                    {
                                        write(nowWr,gson.toJson(now),f1,f2);
                                    }else
                                    {
                                        write(nowWr,"'",f1,f2);
                                        nowWr=!nowWr;
                                        write(nowWr,gson.toJson(now),f1,f2);
                                    }
                                    break;
                                case 3:
                                    if(now.int3>=last.int3)
                                    {
                                        write(nowWr,gson.toJson(now),f1,f2);
                                    }else
                                    {
                                        write(nowWr,"'",f1,f2);
                                        nowWr=!nowWr;
                                        write(nowWr,gson.toJson(now),f1,f2);
                                    }
                                    break;
                                case 4:
                                    if(now.str4.compareTo(last.str4)>=0)
                                    {
                                        write(nowWr,gson.toJson(now),f1,f2);
                                    }else
                                    {
                                        write(nowWr,"'",f1,f2);
                                        nowWr=!nowWr;
                                        write(nowWr,gson.toJson(now),f1,f2);
                                    }
                                    break;
                                case 5:
                                    if(now.str5.compareTo(last.str5)>=0)
                                    {
                                        write(nowWr,gson.toJson(now),f1,f2);
                                    }else
                                    {
                                        write(nowWr,"'",f1,f2);
                                        nowWr=!nowWr;
                                        write(nowWr,gson.toJson(now),f1,f2);
                                    }
                                    break;
                            }
                            f1.flush();
                            f2.flush();
                            last=Cont.copy(now);
                        }
                        in.close();
                        fl.close();
                        f1.close();
                        f2.close();
                        messege("stop divide");
                        nowWr=true;
                        FileWriter obed=new FileWriter(ish);
                        FileReader p1 = new FileReader(n1);
                        BufferedReader p1b= new BufferedReader(p1);
                        FileReader p2 = new FileReader(n2);
                        BufferedReader p2b= new BufferedReader(p2);
                        messege("Start obiedinenie");
                        while((p1b.ready())||(p2b.ready()))
                        {
                            String read;
                            if(nowWr)
                            {
                                while((read=p1b.readLine())!=null)
                                {
                                    if(read.equals("'"))
                                    {
                                        nowWr=changeBool(nowWr);
                                        break;
                                    }else
                                    {
                                        obed.write(read);
                                        obed.write("\n");
                                    }
                                }
                                obed.flush();
                            }else
                            {
                                while((read=p2b.readLine())!=null)
                                {
                                    if(read.equals("'"))
                                    {
                                        nowWr=changeBool(nowWr);
                                        break;
                                    }else
                                    {
                                        obed.write(read);
                                        obed.write("\n");
                                    }
                                }
                                obed.flush();
                            }
                        }
                        obed.flush();
                        obed.close();
                        p1.close();
                        p1b.close();
                        p2.close();
                        p2b.close();
                        messege("stop obed\n Check end");
                        FileReader fileReader= new FileReader(ish);
                        BufferedReader bufferedReader=new BufferedReader(fileReader);
                        last=gson.fromJson(bufferedReader.readLine(),Cont.class);
                        agen=true;
                        while ((red=bufferedReader.readLine())!=null)
                        {
                            now=gson.fromJson(red,Cont.class);
                            switch (t)
                            {
                                case 1:
                                    if(last.int1>now.int1)agen=false;
                                    break;
                                case 2:
                                    if(last.int2>now.int2)agen=false;
                                    break;
                                case 3:
                                    if(last.int3>now.int3)agen=false;
                                    break;
                                case 4:
                                    if(last.str4.compareTo(now.str4)>0)agen=false;
                                    break;
                                case 5:
                                    if(last.str5.compareTo(now.str5)>0)agen=false;
                                    break;
                            }
                            last=Cont.copy(now);
                        }
                        bufferedReader.close();
                        fileReader.close();
                   break;
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    */
    }

}
