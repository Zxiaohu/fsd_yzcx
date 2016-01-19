package com.fsd.yzcx.ui.view;
import com.fsd.yzcx.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**DetailsItemView
 * Created by zxh on 2016/1/19.
 * 订单详情页面的列表项
 */
public class DetailsItemView extends LinearLayout{


    private View view;
    private TextView tv_tip;
    private TextView tv_content1;
    private TextView tv_content2;

    private TextView tv_content1_tip;
    private TextView tv_content2_tip;
    public DetailsItemView(Context context) {
        super(context);
        init(context);
    }


    public DetailsItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DetailsItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        view = View.inflate(context,R.layout.order_details_item,this);
        tv_tip = (TextView) view.findViewById(R.id.tv_tip);

        tv_content1_tip= (TextView) view.findViewById(R.id.tv_tip1);
        tv_content2_tip= (TextView) view.findViewById(R.id.tv_tip2);
        tv_content1 = (TextView) view.findViewById(R.id.tv_content1);
        tv_content2 = (TextView) view.findViewById(R.id.tv_content2);

    }


    /**
     * 
     * @param tip 大标题
     * @param tip1 描述1
     * @param content1 内容1
     * @param tip2 描述2
     * @param content2 内容2
     */
    public void setContent(String tip,String tip1,String content1,String tip2,String content2){
        tv_tip.setText(tip);
        tv_content1_tip.setText(tip1);
        tv_content1.setText(content1);
        tv_content2_tip.setText(tip2);
        tv_content2.setText(content2);
    }

}
