package com.wang.utils;

/**
 * @Author 19225
 * @create 2020/12/23 15:05
 */
public class b {
    public boolean test(DataList118 user) {
        if(user!=null&&user.getCalType().equals("AVG")||user.getCalType().equals("MSD")||user.getCalType().equals("SD")&&user.getData1()!=null&&user.getData2()!=null&&user.getData3()!=null&&user.getData4()!=null&&user.getData5()!=null&&user.getData6()!=null&&user.getCalType()!=null) {
            return true;
        }
        else {
            return false;
        }
    }
    public void validate(DataList118 user) {
        if(user.getData1()!=null&&user.getData2()!=null&&user.getData3()!=null&&user.getData4()!=null&&user.getData5()!=null&&user.getData6()!=null) {
            double [] x = new double[6];
            x[0] = Double.parseDouble(user.getData1());
            x[1] = Double.parseDouble(user.getData2());
            x[2] = Double.parseDouble(user.getData3());
            x[3] = Double.parseDouble(user.getData4());
            x[4] = Double.parseDouble(user.getData5());
            x[5] = Double.parseDouble(user.getData6());
            if(user.getCalType().equals("AVG")) {
                user.setResult(avg(x));
            }
            else if(user.getCalType().equals("SD")){
               user.setResult(Variance(x));
            }
            else if(user.getCalType().equals("xxx")){
                user.setResult(Standard(x));
            }
        }

    }
    //求平均值方法
    public static double avg(double[] x)
    {
        int m = x.length;
        double sum = 0;
        for (int i = 0; i < m ; i++) {
            sum+=x[i];
        }
        return sum/m;
    }
    //求方差的方法
    public static double Variance(double[] x) {
                int m=x.length;
                double sum=0;
                 for(int i=0;i<m;i++){//求和
                         sum+=x[i];
                     }
                 double dAve=sum/m;//求平均值
                 double dVar=0;
                 for(int i=0;i<m;i++){//求方差
                         dVar+=(x[i]-dAve)*(x[i]-dAve);
                     }
                 return dVar/m;
    }
    //求标准差的方法
    public static double Standard(double[] x) {
                 int m=x.length;
                 double sum=0;
                 for(int i=0;i<m;i++){//求和
                         sum+=x[i];
                     }
                 double dAve=sum/m;//求平均值
                 double dVar=0;
                 for(int i=0;i<m;i++){//求方差
                         dVar+=(x[i]-dAve)*(x[i]-dAve);
                     }

                 return Math.sqrt(dVar/m);
             }
}
