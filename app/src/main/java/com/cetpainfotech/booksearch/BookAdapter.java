package com.cetpainfotech.booksearch;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by HP-User on 9/12/2016.
 */
public class BookAdapter extends ArrayAdapter<Book> {
    public static class ViewHolder {
        public ImageView ivCover;
        public TextView tvTitle;
        public TextView tvAuthor;
    }
    public BookAdapter(Context context, ArrayList<Book> abooks) {
        super(context,0,abooks);

    }
    public View getView(int position, View convertView, ViewGroup parent) {
        final Book book=getItem(position);
        ViewHolder viewHolder;
        if (convertView==null) {
            viewHolder=new ViewHolder();
            LayoutInflater inflater= (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       convertView=inflater.inflate(R.layout.item_book,parent,false);
            viewHolder.ivCover= (ImageView) convertView.findViewById(R.id.ivBookCover);
            viewHolder.tvTitle= (TextView) convertView.findViewById(R.id.tvTitle);
            viewHolder.tvAuthor= (TextView) convertView.findViewById(R.id.tvAuthor);
         convertView.setTag(viewHolder);

        }
        else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.tvAuthor.setText(book.getAuthor());
        viewHolder.tvAuthor.setText(book.getAuthor());
        viewHolder.tvTitle.setText(book.getTitle());
        Picasso.with(getContext()).load(Uri.parse(book.getCoverUrl())).error(R.drawable.ic_nocover).into(viewHolder.ivCover);
        return convertView;
    }
}
