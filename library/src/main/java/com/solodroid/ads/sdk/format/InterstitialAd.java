package com.thoughtlamp.ads.sdk.format;

import static com.thoughtlamp.ads.sdk.util.Constant.ADMOB;
import static com.thoughtlamp.ads.sdk.util.Constant.AD_STATUS_ON;
import static com.thoughtlamp.ads.sdk.util.Constant.FACEBOOK;
import static com.thoughtlamp.ads.sdk.util.Constant.FAN;
import static com.thoughtlamp.ads.sdk.util.Constant.FAN_BIDDING_ADMOB;
import static com.thoughtlamp.ads.sdk.util.Constant.FAN_BIDDING_AD_MANAGER;
import static com.thoughtlamp.ads.sdk.util.Constant.GOOGLE_AD_MANAGER;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.admanager.AdManagerInterstitialAd;
import com.google.android.gms.ads.admanager.AdManagerInterstitialAdLoadCallback;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.thoughtlamp.ads.sdk.util.OnInterstitialAdDismissedListener;
import com.thoughtlamp.ads.sdk.util.OnInterstitialAdShowedListener;
import com.thoughtlamp.ads.sdk.util.Tools;

public class InterstitialAd {

    public static class Builder {

        private static final String TAG = "AdNetwork";
        private final Activity activity;
        private com.google.android.gms.ads.interstitial.InterstitialAd adMobInterstitialAd;
        private AdManagerInterstitialAd adManagerInterstitialAd;
        private int retryAttempt;
        private int counter = 1;

        private String adStatus = "";
        private String adNetwork = "";
        private String backupAdNetwork = "";
        private String adMobInterstitialId = "";
        private String googleAdManagerInterstitialId = "";
        private String fanInterstitialId = "";
        private String unityInterstitialId = "";
        private String appLovinInterstitialId = "";
        private String appLovinInterstitialZoneId = "";
        private String mopubInterstitialId = "";
        private String ironSourceInterstitialId = "";
        private String wortiseInterstitialId = "";
        private int placementStatus = 1;
        private int interval = 3;
        private boolean legacyGDPR = false;

        public Builder(Activity activity) {
            this.activity = activity;
        }

        public Builder build() {
            loadInterstitialAd();
            return this;
        }

        public Builder build(OnInterstitialAdDismissedListener onInterstitialAdDismissedListener) {
            loadInterstitialAd(onInterstitialAdDismissedListener);
            return this;
        }

        public void show() {
            showInterstitialAd();
        }

        public void show(OnInterstitialAdShowedListener onInterstitialAdShowedListener, OnInterstitialAdDismissedListener onInterstitialAdDismissedListener) {
            showInterstitialAd(onInterstitialAdShowedListener, onInterstitialAdDismissedListener);
        }

        public Builder setAdStatus(String adStatus) {
            this.adStatus = adStatus;
            return this;
        }

        public Builder setAdNetwork(String adNetwork) {
            this.adNetwork = adNetwork;
            return this;
        }

        public Builder setBackupAdNetwork(String backupAdNetwork) {
            this.backupAdNetwork = backupAdNetwork;
            return this;
        }

        public Builder setAdMobInterstitialId(String adMobInterstitialId) {
            this.adMobInterstitialId = adMobInterstitialId;
            return this;
        }

        public Builder setGoogleAdManagerInterstitialId(String googleAdManagerInterstitialId) {
            this.googleAdManagerInterstitialId = googleAdManagerInterstitialId;
            return this;
        }

        public Builder setFanInterstitialId(String fanInterstitialId) {
            this.fanInterstitialId = fanInterstitialId;
            return this;
        }

        public Builder setUnityInterstitialId(String unityInterstitialId) {
            this.unityInterstitialId = unityInterstitialId;
            return this;
        }

        public Builder setAppLovinInterstitialId(String appLovinInterstitialId) {
            this.appLovinInterstitialId = appLovinInterstitialId;
            return this;
        }

        public Builder setAppLovinInterstitialZoneId(String appLovinInterstitialZoneId) {
            this.appLovinInterstitialZoneId = appLovinInterstitialZoneId;
            return this;
        }

        public Builder setMopubInterstitialId(String mopubInterstitialId) {
            this.mopubInterstitialId = mopubInterstitialId;
            return this;
        }

        public Builder setIronSourceInterstitialId(String ironSourceInterstitialId) {
            this.ironSourceInterstitialId = ironSourceInterstitialId;
            return this;
        }

        public Builder setWortiseInterstitialId(String wortiseInterstitialId) {
            this.wortiseInterstitialId = wortiseInterstitialId;
            return this;
        }

        public Builder setPlacementStatus(int placementStatus) {
            this.placementStatus = placementStatus;
            return this;
        }

        public Builder setInterval(int interval) {
            this.interval = interval;
            return this;
        }

        public Builder setLegacyGDPR(boolean legacyGDPR) {
            this.legacyGDPR = legacyGDPR;
            return this;
        }

        public void loadInterstitialAd() {
            if (adStatus.equals(AD_STATUS_ON) && placementStatus != 0) {
                switch (adNetwork) {
                    case ADMOB:
                    case FAN_BIDDING_ADMOB:
                        com.google.android.gms.ads.interstitial.InterstitialAd.load(activity, adMobInterstitialId, Tools.getAdRequest(activity, legacyGDPR), new InterstitialAdLoadCallback() {
                            @Override
                            public void onAdLoaded(@NonNull com.google.android.gms.ads.interstitial.InterstitialAd interstitialAd) {
                                adMobInterstitialAd = interstitialAd;
                                adMobInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        loadInterstitialAd();
                                    }

                                    @Override
                                    public void onAdFailedToShowFullScreenContent(@NonNull com.google.android.gms.ads.AdError adError) {
                                        Log.d(TAG, "The ad failed to show.");
                                    }

                                    @Override
                                    public void onAdShowedFullScreenContent() {
                                        adMobInterstitialAd = null;
                                        Log.d(TAG, "The ad was shown.");
                                    }
                                });
                                Log.i(TAG, "onAdLoaded");
                            }

                            @Override
                            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                Log.i(TAG, loadAdError.getMessage());
                                adMobInterstitialAd = null;
                                loadBackupInterstitialAd();
                                Log.d(TAG, "Failed load AdMob Interstitial Ad");
                            }
                        });
                        break;

                    case GOOGLE_AD_MANAGER:
                    case FAN_BIDDING_AD_MANAGER:
                        AdManagerInterstitialAd.load(activity, googleAdManagerInterstitialId, Tools.getGoogleAdManagerRequest(), new AdManagerInterstitialAdLoadCallback() {
                            @Override
                            public void onAdLoaded(@NonNull AdManagerInterstitialAd interstitialAd) {
                                super.onAdLoaded(adManagerInterstitialAd);
                                adManagerInterstitialAd = interstitialAd;
                                adManagerInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                                    @Override
                                    public void onAdClicked() {
                                        super.onAdClicked();
                                    }

                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        super.onAdDismissedFullScreenContent();
                                        loadInterstitialAd();
                                    }

                                    @Override
                                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                                        super.onAdFailedToShowFullScreenContent(adError);
                                    }

                                    @Override
                                    public void onAdImpression() {
                                        super.onAdImpression();
                                    }

                                    @Override
                                    public void onAdShowedFullScreenContent() {
                                        super.onAdShowedFullScreenContent();
                                        adManagerInterstitialAd = null;
                                        Log.d(TAG, "The ad was shown.");
                                    }
                                });
                            }

                            @Override
                            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                super.onAdFailedToLoad(loadAdError);
                                adManagerInterstitialAd = null;
                                loadBackupInterstitialAd();
                                Log.d(TAG, "Failed load Ad Manager Interstitial Ad");
                            }
                        });
                        break;

                    default:
                        break;
                }
            }
        }

        public void loadBackupInterstitialAd() {
            if (adStatus.equals(AD_STATUS_ON) && placementStatus != 0) {
                switch (backupAdNetwork) {
                    case ADMOB:
                    case FAN_BIDDING_ADMOB:
                        com.google.android.gms.ads.interstitial.InterstitialAd.load(activity, adMobInterstitialId, Tools.getAdRequest(activity, legacyGDPR), new InterstitialAdLoadCallback() {
                            @Override
                            public void onAdLoaded(@NonNull com.google.android.gms.ads.interstitial.InterstitialAd interstitialAd) {
                                adMobInterstitialAd = interstitialAd;
                                adMobInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        loadInterstitialAd();
                                    }

                                    @Override
                                    public void onAdFailedToShowFullScreenContent(@NonNull com.google.android.gms.ads.AdError adError) {
                                        Log.d(TAG, "The ad failed to show.");
                                    }

                                    @Override
                                    public void onAdShowedFullScreenContent() {
                                        adMobInterstitialAd = null;
                                        Log.d(TAG, "The ad was shown.");
                                    }
                                });
                                Log.i(TAG, "onAdLoaded");
                            }

                            @Override
                            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                Log.i(TAG, loadAdError.getMessage());
                                adMobInterstitialAd = null;
                                Log.d(TAG, "Failed load AdMob Interstitial Ad");
                            }
                        });
                        break;

                    case GOOGLE_AD_MANAGER:
                    case FAN_BIDDING_AD_MANAGER:
                        AdManagerInterstitialAd.load(activity, googleAdManagerInterstitialId, Tools.getGoogleAdManagerRequest(), new AdManagerInterstitialAdLoadCallback() {
                            @Override
                            public void onAdLoaded(@NonNull AdManagerInterstitialAd interstitialAd) {
                                super.onAdLoaded(adManagerInterstitialAd);
                                adManagerInterstitialAd = interstitialAd;
                                adManagerInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                                    @Override
                                    public void onAdClicked() {
                                        super.onAdClicked();
                                    }

                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        super.onAdDismissedFullScreenContent();
                                        loadInterstitialAd();
                                    }

                                    @Override
                                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                                        super.onAdFailedToShowFullScreenContent(adError);
                                    }

                                    @Override
                                    public void onAdImpression() {
                                        super.onAdImpression();
                                    }

                                    @Override
                                    public void onAdShowedFullScreenContent() {
                                        super.onAdShowedFullScreenContent();
                                        adManagerInterstitialAd = null;
                                        Log.d(TAG, "The ad was shown.");
                                    }
                                });
                            }

                            @Override
                            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                super.onAdFailedToLoad(loadAdError);
                                adManagerInterstitialAd = null;
                                Log.d(TAG, "Failed load Ad Manager Interstitial Ad");
                            }
                        });
                        break;

                    default:
                        break;
                }
            }
        }

        public void showInterstitialAd() {
            if (adStatus.equals(AD_STATUS_ON) && placementStatus != 0) {
                if (counter == interval) {
                    switch (adNetwork) {
                        case ADMOB:
                        case FAN_BIDDING_ADMOB:
                            if (adMobInterstitialAd != null) {
                                adMobInterstitialAd.show(activity);
                                Log.d(TAG, "admob interstitial not null");
                            } else {
                                showBackupInterstitialAd();
                                Log.d(TAG, "admob interstitial null");
                            }
                            break;

                        case GOOGLE_AD_MANAGER:
                        case FAN_BIDDING_AD_MANAGER:
                            if (adManagerInterstitialAd != null) {
                                adManagerInterstitialAd.show(activity);
                                Log.d(TAG, "ad manager interstitial not null");
                            } else {
                                showBackupInterstitialAd();
                                Log.d(TAG, "ad manager interstitial null");
                            }
                            break;

                        default:
                            break;
                    }
                    counter = 1;
                } else {
                    counter++;
                }
                Log.d(TAG, "Current counter : " + counter);
            }
        }

        public void showBackupInterstitialAd() {
            if (adStatus.equals(AD_STATUS_ON) && placementStatus != 0) {
                Log.d(TAG, "Show Backup Interstitial Ad [" + backupAdNetwork.toUpperCase() + "]");
                switch (backupAdNetwork) {
                    case ADMOB:
                    case FAN_BIDDING_ADMOB:
                        if (adMobInterstitialAd != null) {
                            adMobInterstitialAd.show(activity);
                        }
                        break;

                    case GOOGLE_AD_MANAGER:
                    case FAN_BIDDING_AD_MANAGER:
                        if (adManagerInterstitialAd != null) {
                            adManagerInterstitialAd.show(activity);
                        }
                        break;

                    default:
                        break;
                }
            }
        }

        public void loadInterstitialAd(OnInterstitialAdDismissedListener onInterstitialAdDismissedListener) {
            if (adStatus.equals(AD_STATUS_ON) && placementStatus != 0) {
                switch (adNetwork) {
                    case ADMOB:
                    case FAN_BIDDING_ADMOB:
                        com.google.android.gms.ads.interstitial.InterstitialAd.load(activity, adMobInterstitialId, Tools.getAdRequest(activity, legacyGDPR), new InterstitialAdLoadCallback() {
                            @Override
                            public void onAdLoaded(@NonNull com.google.android.gms.ads.interstitial.InterstitialAd interstitialAd) {
                                adMobInterstitialAd = interstitialAd;
                                adMobInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        loadInterstitialAd(onInterstitialAdDismissedListener);
                                        onInterstitialAdDismissedListener.onInterstitialAdDismissed();
                                    }

                                    @Override
                                    public void onAdFailedToShowFullScreenContent(@NonNull com.google.android.gms.ads.AdError adError) {
                                        Log.d(TAG, "The ad failed to show.");
                                    }

                                    @Override
                                    public void onAdShowedFullScreenContent() {
                                        adMobInterstitialAd = null;
                                        Log.d(TAG, "The ad was shown.");
                                    }
                                });
                                Log.i(TAG, "onAdLoaded");
                            }

                            @Override
                            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                Log.i(TAG, loadAdError.getMessage());
                                adMobInterstitialAd = null;
                                loadBackupInterstitialAd(onInterstitialAdDismissedListener);
                                Log.d(TAG, "Failed load AdMob Interstitial Ad");
                            }
                        });
                        break;

                    case GOOGLE_AD_MANAGER:
                    case FAN_BIDDING_AD_MANAGER:
                        AdManagerInterstitialAd.load(activity, googleAdManagerInterstitialId, Tools.getGoogleAdManagerRequest(), new AdManagerInterstitialAdLoadCallback() {
                            @Override
                            public void onAdLoaded(@NonNull AdManagerInterstitialAd interstitialAd) {
                                super.onAdLoaded(adManagerInterstitialAd);
                                adManagerInterstitialAd = interstitialAd;
                                adManagerInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                                    @Override
                                    public void onAdClicked() {
                                        super.onAdClicked();
                                    }

                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        super.onAdDismissedFullScreenContent();
                                        loadInterstitialAd(onInterstitialAdDismissedListener);
                                        onInterstitialAdDismissedListener.onInterstitialAdDismissed();
                                    }

                                    @Override
                                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                                        super.onAdFailedToShowFullScreenContent(adError);
                                    }

                                    @Override
                                    public void onAdImpression() {
                                        super.onAdImpression();
                                    }

                                    @Override
                                    public void onAdShowedFullScreenContent() {
                                        super.onAdShowedFullScreenContent();
                                        adManagerInterstitialAd = null;
                                        Log.d(TAG, "The ad was shown.");
                                    }
                                });
                            }

                            @Override
                            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                super.onAdFailedToLoad(loadAdError);
                                adManagerInterstitialAd = null;
                                loadBackupInterstitialAd(onInterstitialAdDismissedListener);
                                Log.d(TAG, "Failed load Ad Manager Interstitial Ad");
                            }
                        });
                        break;

                    default:
                        break;
                }
            }
        }

        public void loadBackupInterstitialAd(OnInterstitialAdDismissedListener onInterstitialAdDismissedListener) {
            if (adStatus.equals(AD_STATUS_ON) && placementStatus != 0) {
                switch (backupAdNetwork) {
                    case ADMOB:
                    case FAN_BIDDING_ADMOB:
                        com.google.android.gms.ads.interstitial.InterstitialAd.load(activity, adMobInterstitialId, Tools.getAdRequest(activity, legacyGDPR), new InterstitialAdLoadCallback() {
                            @Override
                            public void onAdLoaded(@NonNull com.google.android.gms.ads.interstitial.InterstitialAd interstitialAd) {
                                adMobInterstitialAd = interstitialAd;
                                adMobInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        loadInterstitialAd(onInterstitialAdDismissedListener);
                                        onInterstitialAdDismissedListener.onInterstitialAdDismissed();
                                    }

                                    @Override
                                    public void onAdFailedToShowFullScreenContent(@NonNull com.google.android.gms.ads.AdError adError) {
                                        Log.d(TAG, "The ad failed to show.");
                                    }

                                    @Override
                                    public void onAdShowedFullScreenContent() {
                                        adMobInterstitialAd = null;
                                        Log.d(TAG, "The ad was shown.");
                                    }
                                });
                                Log.i(TAG, "onAdLoaded");
                            }

                            @Override
                            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                Log.i(TAG, loadAdError.getMessage());
                                adMobInterstitialAd = null;
                                Log.d(TAG, "Failed load AdMob Interstitial Ad");
                            }
                        });
                        break;

                    case GOOGLE_AD_MANAGER:
                    case FAN_BIDDING_AD_MANAGER:
                        AdManagerInterstitialAd.load(activity, googleAdManagerInterstitialId, Tools.getGoogleAdManagerRequest(), new AdManagerInterstitialAdLoadCallback() {
                            @Override
                            public void onAdLoaded(@NonNull AdManagerInterstitialAd interstitialAd) {
                                super.onAdLoaded(adManagerInterstitialAd);
                                adManagerInterstitialAd = interstitialAd;
                                adManagerInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                                    @Override
                                    public void onAdClicked() {
                                        super.onAdClicked();
                                    }

                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        super.onAdDismissedFullScreenContent();
                                        loadInterstitialAd(onInterstitialAdDismissedListener);
                                        onInterstitialAdDismissedListener.onInterstitialAdDismissed();
                                    }

                                    @Override
                                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                                        super.onAdFailedToShowFullScreenContent(adError);
                                    }

                                    @Override
                                    public void onAdImpression() {
                                        super.onAdImpression();
                                    }

                                    @Override
                                    public void onAdShowedFullScreenContent() {
                                        super.onAdShowedFullScreenContent();
                                        adManagerInterstitialAd = null;
                                        Log.d(TAG, "The ad was shown.");
                                    }
                                });
                            }

                            @Override
                            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                super.onAdFailedToLoad(loadAdError);
                                adManagerInterstitialAd = null;
                                Log.d(TAG, "Failed load Ad Manager Interstitial Ad");
                            }
                        });
                        break;

                    default:
                        break;
                }
            }
        }

        public void showInterstitialAd(OnInterstitialAdShowedListener onInterstitialAdShowedListener, OnInterstitialAdDismissedListener onInterstitialAdDismissedListener) {
            if (adStatus.equals(AD_STATUS_ON) && placementStatus != 0) {
                if (counter == interval) {
                    switch (adNetwork) {
                        case ADMOB:
                        case FAN_BIDDING_ADMOB:
                            if (adMobInterstitialAd != null) {
                                adMobInterstitialAd.show(activity);
                                onInterstitialAdShowedListener.onInterstitialAdShowed();
                                Log.d(TAG, "admob interstitial not null");
                            } else {
                                showBackupInterstitialAd(onInterstitialAdShowedListener, onInterstitialAdDismissedListener);
                                Log.d(TAG, "admob interstitial null");
                            }
                            break;

                        case GOOGLE_AD_MANAGER:
                        case FAN_BIDDING_AD_MANAGER:
                            if (adManagerInterstitialAd != null) {
                                adManagerInterstitialAd.show(activity);
                                onInterstitialAdShowedListener.onInterstitialAdShowed();
                                Log.d(TAG, "ad manager interstitial not null");
                            } else {
                                showBackupInterstitialAd(onInterstitialAdShowedListener, onInterstitialAdDismissedListener);
                                Log.d(TAG, "ad manager interstitial null");
                            }
                            break;

                        default:
                            break;
                    }
                    counter = 1;
                } else {
                    onInterstitialAdDismissedListener.onInterstitialAdDismissed();
                    counter++;
                }
                Log.d(TAG, "Current counter : " + counter);
            } else {
                onInterstitialAdDismissedListener.onInterstitialAdDismissed();
            }
        }

        public void showBackupInterstitialAd(OnInterstitialAdShowedListener onInterstitialAdShowedListener, OnInterstitialAdDismissedListener onInterstitialAdDismissedListener) {
            if (adStatus.equals(AD_STATUS_ON) && placementStatus != 0) {
                Log.d(TAG, "Show Backup Interstitial Ad [" + backupAdNetwork.toUpperCase() + "]");
                switch (backupAdNetwork) {
                    case ADMOB:
                    case FAN_BIDDING_ADMOB:
                        if (adMobInterstitialAd != null) {
                            adMobInterstitialAd.show(activity);
                            onInterstitialAdShowedListener.onInterstitialAdShowed();
                        }
                        break;

                    case GOOGLE_AD_MANAGER:
                    case FAN_BIDDING_AD_MANAGER:
                        if (adManagerInterstitialAd != null) {
                            adManagerInterstitialAd.show(activity);
                            onInterstitialAdShowedListener.onInterstitialAdShowed();
                        }
                        break;

                    default:
                        break;
                }
            } else {
                onInterstitialAdDismissedListener.onInterstitialAdDismissed();
            }
        }

    }

}
