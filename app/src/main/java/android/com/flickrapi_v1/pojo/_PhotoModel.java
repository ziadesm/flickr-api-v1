package android.com.flickrapi_v1.pojo;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;

public class _PhotoModel {

    private Photos photos;
    private String stat;

    public Photos getPhotos() {
        return photos;
    }

    public void setPhotos(Photos photos) {
        this.photos = photos;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public static class Photos {

        private int page;
        private int pages;
        private int perpage;
        public List<Photo> photo;
        private String total;

        public Photos() {
        }

        public List<Photo> getPhoto() {
            return photo;
        }

        public void setPhoto(List<Photo> photo) {
            this.photo = photo;
        }

        public static class Photo {

            private int farm;
            private String id;
            private int isfamily;
            private String url_s;
            private int isfriend;
            private int ispublic;
            private String owner;
            private String secret;
            private String server;
            private String title;

            public static DiffUtil.ItemCallback<Photos.Photo> CALLBACK = new DiffUtil.ItemCallback<Photo>() {
                @Override
                public boolean areItemsTheSame(@androidx.annotation.NonNull Photo oldItem
                        , @androidx.annotation.NonNull Photo newItem) {
                    return oldItem.id == newItem.id;
                }

                @Override
                public boolean areContentsTheSame(@androidx.annotation.NonNull Photo oldItem
                        , @androidx.annotation.NonNull Photo newItem) {
                    return true;
                }
            };

            public String getUrl_s() {
                return url_s;
            }

            public void setUrl_s(String url_s) {
                this.url_s = url_s;
            }

            public int getFarm() {
                return farm;
            }

            public void setFarm(int farm) {
                this.farm = farm;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public int getIsfamily() {
                return isfamily;
            }

            public void setIsfamily(int isfamily) {
                this.isfamily = isfamily;
            }

            public int getIsfriend() {
                return isfriend;
            }

            public void setIsfriend(int isfriend) {
                this.isfriend = isfriend;
            }

            public int getIspublic() {
                return ispublic;
            }

            public void setIspublic(int ispublic) {
                this.ispublic = ispublic;
            }

            public String getOwner() {
                return owner;
            }

            public void setOwner(String owner) {
                this.owner = owner;
            }

            public String getSecret() {
                return secret;
            }

            public void setSecret(String secret) {
                this.secret = secret;
            }

            public String getServer() {
                return server;
            }

            public void setServer(String server) {
                this.server = server;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

        }
    }
}
