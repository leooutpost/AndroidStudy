package bean;

/**
 * Created by 乐毅 on 2016-08-30.
 */
public class MusicMedia {

    private int id;    //音乐id
    private String title;  //音乐标题
    private String artist;//音乐作者
    private String url; //音乐存放路径
    private String time; //音乐时长
    private String size; //音乐大小
    private int albumId; //专辑id
    private String album; //专辑

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getArtist() {
        return artist;
    }
    public void setArtist(String artist) {
        this.artist = artist;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getTime() {
        return time;
    }
    //修改时间格式.  @time ：以ms为单位的音乐时长，
    public void setTime(int time) {
        time /= 1000;
        int minute = time / 60;
        int hour = minute / 60;
        int second = time % 60;
        minute %= 60;
        this.time = String.format("%02d:%02d", minute, second);
    }
    public String getSize() {
        return size;
    }
    public void setSize(Long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;
        if (size >= gb) {
            this.size = String.format("%.1f GB", (float) size / gb);//%.1f ,(float) size / gb保留一位小数
        } else if (size >= mb) {
            float f = (float) size / mb;
            this.size = String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            this.size = String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else
            this.size = String.format("%d B", size);
    }
    public int getAlbumId() {
        return albumId;
    }
    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }
    public String getAlbum() {
        return album;
    }
    public void setAlbum(String album) {
        this.album = album;
    }
}