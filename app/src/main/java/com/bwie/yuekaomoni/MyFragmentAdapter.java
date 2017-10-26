package com.bwie.yuekaomoni;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import imageloader.bwie.com.imageloaderlibrary.UtilImage;


/**
 * 作者：张玉轲
 * 时间：2017/10/25
 */

public class MyFragmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private List<Bean.DataBean> list;

    public MyFragmentAdapter(Context context, List<Bean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        View view = View.inflate(context, R.layout.fragment1_item, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder){
            MyViewHolder myViewHolder= (MyViewHolder) holder;
            myViewHolder.tv.setText(list.get(position).getIntroduction());
            getLoadiImg(list.get(position).getUserImg(),myViewHolder.iv);
           //TwoImageUtils.loadImage(list.get(position).getUserImg(),myViewHolder.iv);
            ImageLoader.getInstance().displayImage(list.get(position).getUserImg(),myViewHolder.iv, UtilImage.getOptions());
            //Glide.with(context).load(list.get(position).getUserImg()).into(myViewHolder.iv);

        }
    }

    private void getLoadiImg(String url, final ImageView img){
        new AsyncTask<String,Void,Bitmap>(){
            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                if (bitmap != null){

                    img.setImageBitmap(bitmap);

                }else {

                    img.setImageResource(R.mipmap.ic_launcher_round);

                }
            }

            @Override
            protected Bitmap doInBackground(String... strings) {
                try {

                    //得到请求地址
                    String path = strings[0];

                    URL url = new URL(path);

                    //打开链接
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    //设置请求方式get
                    connection.setRequestMethod("GET");

                    //设置打开链接的时间
                    connection.setConnectTimeout(5000);

                    //设置读取数据返回的时间
                    connection.setReadTimeout(5000);

                    //得到状态码
                    int code = connection.getResponseCode();

                    if (code == 200){



                        InputStream is = connection.getInputStream();

                        return  getBitmap(is,url,50,50);
     }

                } catch (Exception e) {

                    e.printStackTrace();

                }



                return null;

            }
        }.execute(url);
    }


    private Bitmap getBitmap(InputStream is, URL url, int requsetHeight, int requsetWidth){

        try {

            //将字节流转化成图片对象
            //得到图片的宽高
            BitmapFactory.Options options = new BitmapFactory.Options();

            //假解析图片
            options.inJustDecodeBounds = true;

            BitmapFactory.decodeStream(is,null,options);

            //得到采样率
            int inSampleSize = getinSampleSize(options, requsetHeight, requsetWidth);

            //将得到的采样率赋值
            options.inSampleSize = inSampleSize;

            //得到采样率后进行真解析
            options.inJustDecodeBounds = false;

            //重新打开流
            is.close();

            InputStream inputStream = url.openStream();

            //得到压缩后的图片
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream,null,options);

            return  bitmap;

        } catch (IOException e) {

            e.printStackTrace();

        }

        return  null;

    }

    private int getinSampleSize(BitmapFactory.Options options,int requsetHeight,int requsetWidth){

        //得到图片的原始宽高
        int height = options.outHeight;

        int width = options.outWidth;

        //定义一个变量记录采样率
        int inSampleSize = 1;

        //判断是否在期望的宽高内
        if (height > requsetHeight || width > requsetWidth){


            int helfHeight = height / 2;

            int helfWidth = width /2;

            //计算采样率
            while (helfHeight/inSampleSize > requsetHeight && helfWidth/inSampleSize >requsetWidth){

                inSampleSize *= 2;

            }

        }

        return inSampleSize;

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv;
        ImageView iv;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv=itemView.findViewById(R.id.tv);
           iv= itemView.findViewById(R.id.iv);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener!=null){
                        listener.setListener(view,getPosition());
                    }
                }
            });

        }
    }

    public SetDianjiListener listener;
    public void SetDianjiListener(SetDianjiListener listener){
        this.listener=listener;

    }
    public interface SetDianjiListener{
        void setListener(View v,int po);
    }
}
