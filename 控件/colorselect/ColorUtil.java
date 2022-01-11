package com.carocean.ckxcarsettings.colorselect;


/**
 * @author Macro
 * @date 2021/7/14
 * @describe
 */
public class ColorUtil {
    public static int[][] colorsRgb = {
            {0, 255, 0},
            {0, 0, 255},
            {255, 0, 0},
            {68, 0, 187},
            {53, 0, 202},
            {36, 0, 219},
            {18, 0, 237},
            {0, 17, 238},
            {0, 33, 222},
            {0, 49, 206},
            {0, 63, 192},
            {0, 76, 179},
            {0, 88, 167},
            {0, 98, 157},
            {0, 108, 147},
            {0, 116, 139},
            {0, 124, 131},
            {0, 131, 124},
            {0, 140, 115},
            {0, 149, 106},
            {0, 160, 95},
            {0, 172, 83},
            {0, 185, 70},
            {0, 199, 56},
            {0, 214, 41},
            {0, 230, 25},
            {0, 248, 7},
            {19, 236, 0},
            {37, 218, 0},
            {54, 201, 0},
            {69, 186, 0},
            {83, 172, 0},
            {96, 159, 0},
            {107, 148, 0},
            {117, 138, 0},
            {126, 129, 0},
            {134, 121, 0},
            {142, 113, 0},
            {149, 106, 0},
            {158, 97, 0},
            {168, 87, 0},
            {180, 75, 0},
            {193, 62, 0},
            {207, 48, 0},
            {222, 33, 0},
            {238, 17, 0},
            {242, 0, 13},
            {237, 0, 18},
            {220, 0, 35},
            {205, 0, 50},
            {191, 0, 64},
            {178, 0, 77},
            {167, 0, 88},
            {156, 0, 99},
            {147, 0, 108},
            {140, 0, 115},
            {133, 0, 122},
            {126, 0, 129},
            {117, 0, 138},
            {106, 0, 149},
            {95, 0, 160},
            {82, 0, 173},
            {93, 93, 70},
            {240, 246, 213}
    };

//    public static int[] colorsDrawableIds = {
//            R.drawable.color_1, R.drawable.color_2, R.drawable.color_3, R.drawable.color_4, R.drawable.color_5,
//            R.drawable.color_6, R.drawable.color_7, R.drawable.color_8, R.drawable.color_9, R.drawable.color_10,
//            R.drawable.color_11, R.drawable.color_12, R.drawable.color_13, R.drawable.color_14, R.drawable.color_15,
//            R.drawable.color_16, R.drawable.color_17, R.drawable.color_18, R.drawable.color_19, R.drawable.color_20,
//            R.drawable.color_21, R.drawable.color_22, R.drawable.color_23, R.drawable.color_24, R.drawable.color_25,
//            R.drawable.color_26, R.drawable.color_27, R.drawable.color_28, R.drawable.color_29, R.drawable.color_30,
//            R.drawable.color_31, R.drawable.color_32, R.drawable.color_33, R.drawable.color_34, R.drawable.color_35,
//            R.drawable.color_36, R.drawable.color_37, R.drawable.color_38, R.drawable.color_39, R.drawable.color_40,
//            R.drawable.color_41, R.drawable.color_42, R.drawable.color_43, R.drawable.color_44, R.drawable.color_45,
//            R.drawable.color_46, R.drawable.color_47, R.drawable.color_48, R.drawable.color_49, R.drawable.color_50,
//            R.drawable.color_51, R.drawable.color_52, R.drawable.color_53, R.drawable.color_54, R.drawable.color_55,
//            R.drawable.color_56, R.drawable.color_57, R.drawable.color_58, R.drawable.color_59, R.drawable.color_60,
//            R.drawable.color_61, R.drawable.color_62, R.drawable.color_63, R.drawable.color_64
//    };

    public static int [][] recommendColorXy = {
	        {72, 24},
            {146, 105},
            {39, 127},
            {134, 127},
            {138, 122},
            {141, 166},
            {144, 111},
            {148, 99},
            {149, 93},
            {149, 86},
            {148, 74},
            {147, 67},
            {145, 62},
            {142, 56},
            {139, 50},
            {135, 45},
            {130, 41},
            {126, 36},
            {121, 33},
            {115, 29},
            {109, 27},
            {103, 25},
            {100, 24},
            {97, 23},
            {91, 22},
            {85, 22},
            {78, 22},
            {66, 25},
            {60, 27},
            {54, 30},
            {49, 34},
            {44, 38},
            {39, 42},
            {35, 48},
            {31, 53},
            {28, 58},
            {24, 70},
            {23, 77},
            {22, 83},
            {22, 89},
            {22, 96},
            {23, 102},
            {25, 108},
            {28, 113},
            {31, 119},
            {34, 124},
            {44, 133},
            {49, 137},
            {54, 141},
            {60, 143},
            {66, 145},
            {72, 148},
            {78, 149},
            {84, 149},
            {90, 149},
            {96, 148},
            {103, 147},
            {108, 148},
            {114, 142},
            {120, 139},
            {125, 135},
            {129, 131},
            {26, 65},
            {86, 86}
    };

    public static double distanceTo(int[] curRgb,int[] desRgb) {
        final int rmen = (curRgb[0] + desRgb[0]) / 2;
        final int deltaR = curRgb[0] - desRgb[0];
        final int deltaG = curRgb[1] -desRgb[1];
        final int deltaB = curRgb[2] - desRgb[2];
        return Math.sqrt((2 + rmen / 256) * deltaR * deltaR + 4 * deltaG * deltaG +
                (2 + (255 - rmen) / 256) * deltaB * deltaB);

    }

    public static double distanceXYTo(int[] curRgb,int[] desRgb) {
        double[] desCurrent = rgbToXy(curRgb[0],curRgb[1],curRgb[2]);
        double[] current = rgbToXy(desRgb[0],desRgb[1],desRgb[2]);

        return Math.hypot(desCurrent[0]-current[0],desCurrent[1]-current[1]);

    }

    public static int findClosestPaletteColorTo(int[] desRgb) {
        double closestDistance = Integer.MAX_VALUE;
        int index = 0 ;
        for (int i = 0 ; i <colorsRgb.length;i++) {
            int[] curRgb = colorsRgb[i];
            final double distance = distanceXYTo(curRgb,desRgb);
            if (distance < closestDistance) {
                closestDistance = distance;
                index = i;
            }
        }
        return index;
    }

    public static double[] rgbToXy(int r, int g, int b){
        double gammaX = gamma(r);
        double gammaY = gamma(g);
        double gammaZ = gamma(b);
        double x = gammaX/(gammaX+gammaY+gammaZ);
        double y = gammaY/(gammaX+gammaY+gammaZ);
        return new double[]{x, y};
    }

    private static double gamma(int num){
        if (num>0.04045){
            return Math.pow((num + 0.055) / 1.055,2.4);
        }else {
            return (num / 12.92);
        }
    }


}
