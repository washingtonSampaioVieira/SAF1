package com.senai.sp.conversores;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.ByteArrayOutputStream;

public class Imagem {
    public static Bitmap arrayToBitmap(byte[] imagemArray){
        Bitmap bitmap = BitmapFactory.decodeByteArray(imagemArray, 0, imagemArray.length);

        return  bitmap;
    }
    public static byte[] BitmapToArray(Drawable drawable){

        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();

        Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmapReduzido.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
        byte[] fotoArray = byteArrayOutputStream.toByteArray();

        return fotoArray;
    }

}
