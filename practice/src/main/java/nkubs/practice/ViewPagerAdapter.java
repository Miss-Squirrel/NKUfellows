package nkubs.practice;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


public class ViewPagerAdapter extends PagerAdapter{

    private List<View> viewList;
    private List<String> tabList;

    public ViewPagerAdapter(List<View>viewList,List<String>tabList)
    {
        this.viewList = viewList;
        this.tabList = tabList;
    }

    //返回的是页卡的数量
    @Override
    public int getCount() {
        return viewList.size();
    }


    //判断当前的View是否来自于对象
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    //实例化一个页卡
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(viewList.get(position));
        return viewList.get(position);
    }


    //销毁一个页卡
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewList.get(position));
    }

    //设置标题
    @Override
    public CharSequence getPageTitle(int position) {
        return tabList.get(position);
    }
}
