package com.igormelo.challenge.Models.PullRequests;

import com.google.gson.annotations.SerializedName;
import com.igormelo.challenge.Repo;

/**
 * Created by root on 27/01/17.
 */
public class Head {
        @SerializedName("label")
        private String label;
        @SerializedName("ref")
        private String ref;
        @SerializedName("sha")
        private String sha;
        @SerializedName("user")
        private UserX user;
        @SerializedName("repo")
        private Repo repo;

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getRef() {
            return ref;
        }

        public void setRef(String ref) {
            this.ref = ref;
        }

        public String getSha() {
            return sha;
        }

        public void setSha(String sha) {
            this.sha = sha;
        }

        public UserX getUser() {
            return user;
        }

        public void setUser(UserX user) {
            this.user = user;
        }

        public Repo getRepo() {
            return repo;
        }

        public void setRepo(Repo repo) {
            this.repo = repo;
        }
}
