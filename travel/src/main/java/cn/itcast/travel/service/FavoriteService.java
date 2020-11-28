package cn.itcast.travel.service;

public interface FavoriteService {
    public boolean isFavorite(int uid,int rid);

    public void addFavorite(int uid, int rid);
}
