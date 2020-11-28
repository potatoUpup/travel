package cn.itcast.travel.dao;

public interface FavoriteDao {
    public boolean isFavorite(int uid,int rid);

    public void addFavorite(int uid, int rid);
}
