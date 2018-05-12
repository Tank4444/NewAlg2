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

        int pred = 100;
        n = pred;
        nSchet = nVsego;
        while (n <= nSchet) {
            list = createList(n, pred);
            saveInFile(list, fileIn, true);
            ArrayList<container> res = sort(f, s, t, list);
            saveInFile(res, fileSort, true);
            nSchet -= n;
        }
        list = createList(nSchet, pred);
        saveInFile(list, fileIn, true);
        ArrayList<container> res = sort(f, s, t, list);
        saveInFile(res, fileSort, true);

        //start work
        copy(fileSort, fileCopyPostSort, false);
        File fl = sort(f, s, t, fileSort);
        File output = new File("output");
        copy(fl, output, false);


    }

    File sort(int f, int s, int t, File file) {
        int n = 0;
        File pred = new File("sort1");
        File prom;
        copy(file, pred, false);
        File[] splited;
        do {
            n++;
            splited = split(f, pred);
            prom = merge(splited, f);
            copy(prom, pred, false);
            System.out.println("this is " + n + " time with " + series + " seriese and file is " + fileIsEmpty(splited[1]));
        } while (!fileIsEmpty(splited[1]));
        File result = new File("result1");
        File promesh = new File("promesh1");
        try {

            FileReader fr = new FileReader(pred);
            BufferedReader br = new BufferedReader(fr);
            FileWriter fw = new FileWriter(result);
            FileWriter fw1 = new FileWriter(promesh);

            String current = br.readLine();
            container last = new container(current.split(" "));
            fw1.write(current + "\n");
            fw1.flush();
            container carcont;
            while ((current = br.readLine()) != null) {
                carcont = new container(current.split(" "));
                if (sravnenie(last, carcont, f) != 0) {
                    File sort = sort(s, t, promesh);
                    copy(sort, result, true);
                    promesh.delete();
                    promesh = new File("promesh1");
                    fw1 = new FileWriter(promesh);
                }

                fw1.write(current + "\n");
                fw1.flush();
                last = carcont;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return result;
    }

    File sort(int s, int t, File file) {
        File pred = new File("sort2");
        File prom;
        copy(file, pred, false);
        File[] splited;
        do {
            splited = split(s, pred);
            prom = merge(splited, s);
            copy(prom, pred, false);
        } while (!fileIsEmpty(splited[1]));
        File result = new File("result2");
        File promesh = new File("promesh2");
        try {

            FileReader fr = new FileReader(pred);
            BufferedReader br = new BufferedReader(fr);
            FileWriter fw = new FileWriter(result);
            FileWriter fw1 = new FileWriter(promesh);

            String current = br.readLine();
            container last = new container(current.split(" "));
            fw1.write(current + "\n");
            fw1.flush();
            container carcont;
            while ((current = br.readLine()) != null) {
                carcont = new container(current.split(" "));
                if (sravnenie(last, carcont, s) != 0) {
                    File sort = sort(t, promesh);
                    copy(sort, result, true);
                    promesh.delete();
                    promesh = new File("promesh2");
                    fw1 = new FileWriter(promesh);
                }

                fw1.write(current + "\n");
                fw1.flush();
                last = carcont;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    File sort(int t, File file) {
        File result = new File("sort3");
        File prom;
        copy(file, result, false);
        File[] splited;
        do {
            n++;
            splited = split(t, result);
            prom = merge(splited, t);
            copy(prom, result, false);
        } while (!fileIsEmpty(splited[1]));
        return result;
    }


    File[] split(int param, File fileIn) {
        boolean dir = true;
        boolean thisIsFirst = true;
        File left = new File("left");
        left.delete();
        File right = new File("right");
        left.delete();
        try {
            FileReader fileReader = new FileReader(fileIn);
            BufferedReader reader = new BufferedReader(fileReader);
            FileWriter leftFile = new FileWriter(left, false);
            FileWriter rightFile = new FileWriter(right, false);
            String string = reader.readLine();
            String[] last = string.split(" ");
            leftFile.write(string + "\n");
            series = 1;
            while ((string = reader.readLine()) != null) {
                String[] now = string.split(" ");
                switch (param - 1) {
                    case 0:
                        if (last[0].compareTo(now[0]) > 0) {
                            dir = !dir;
                            series++;
                            if (!thisIsFirst) {
                                if (dir) leftFile.write("-" + "\n");
                                else rightFile.write("-" + "\n");
                            } else thisIsFirst = !thisIsFirst;
                        }
                        break;
                    case 1:
                        if (last[1].compareTo(now[1]) > 0) {
                            dir = !dir;
                            series++;
                            if (!thisIsFirst) {
                                if (dir) leftFile.write("-" + "\n");
                                else rightFile.write("-" + "\n");
                            } else thisIsFirst = !thisIsFirst;
                        }
                        break;
                    case 2:
                        if (last[2].compareTo(now[2]) > 0) {
                            dir = !dir;
                            series++;
                            if (!thisIsFirst) {
                                if (dir) leftFile.write("-" + "\n");
                                else rightFile.write("-" + "\n");
                            } else thisIsFirst = !thisIsFirst;
                        }
                        break;
                    case 3:
                        if (last[3].compareTo(now[3]) > 0) {
                            dir = !dir;
                            series++;
                            if (!thisIsFirst) {
                                if (dir) leftFile.write("-" + "\n");
                                else rightFile.write("-" + "\n");
                            } else thisIsFirst = !thisIsFirst;
                        }
                        break;
                    case 4:
                        if (last[4].compareTo(now[4]) > 0) {
                            dir = !dir;
                            series++;
                            if (!thisIsFirst) {
                                if (dir) leftFile.write("-" + "\n");
                                else rightFile.write("-" + "\n");
                            } else thisIsFirst = !thisIsFirst;
                        }
                        break;
                    default:
                        break;
                }
                if (dir) {
                    leftFile.write(string + "\n");
                } else {
                    rightFile.write(string + "\n");
                }
                last = now;
                leftFile.flush();
                rightFile.flush();
            }
            leftFile.close();
            rightFile.close();

            return new File[]{left, right};
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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

    File merge(File[] files,int p)
    {
        File res=new File("connect");
        try {
            FileReader fr1 = new FileReader(files[0]);
            FileReader fr2=new FileReader(files[1]);

            BufferedReader bf1=new BufferedReader(fr1);
            BufferedReader bf2=new BufferedReader(fr2);

            FileWriter fileWriter=new FileWriter(res);
            String str1=bf1.readLine(),str2=bf2.readLine();
            do {
                //если 1 строка равна порогу, заносим строки 2 файла в основной, пока не дойдем до порога
                if(str1==null)
                {
                    while(str2!=null)
                    {
                        if(!str2.equals("-"))fileWriter.write(str2+"\n");
                        str2=bf2.readLine();
                    }
                }
                    else
                if (str1.equals("-")) {
                    while (!str2.equals("-")) {
                        fileWriter.write(str2+"\n");
                        str2=bf2.readLine();
                        if(str2==null)break;
                    }
                    str1=bf1.readLine();
                    str2=bf2.readLine();
                }
                else
                    //если 2 строка равна порогу, заносим строки 1 файла в основной, пока не дойдем до порога
                    if(str2==null)
                    {
                        while(str1!=null)
                        {
                            if(!str1.equals("-"))fileWriter.write(str1+"\n");
                            str1=bf1.readLine();
                        }
                    } else
                    if (str2.equals("-")) {
                        while (!str1.equals("-")) {
                            fileWriter.write(str1+"\n");
                            str1=bf1.readLine();
                            if(str1==null)break;
                        }

                        str1=bf1.readLine();
                        str2=bf2.readLine();
                    }
                    //сравниваем 2 строки
                    else {


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

            } while (str1!=null || str2!=null);




        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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
