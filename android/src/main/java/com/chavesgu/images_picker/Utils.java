package com.chavesgu.images_picker;

import android.app.Activity;
import android.content.pm.ActivityInfo;

import com.luck.picture.lib.PictureSelectionModel;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.language.LanguageConfig;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.model.AspectRatio;
import com.yalantis.ucrop.view.CropImageView;
import com.yalantis.ucrop.view.OverlayView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Utils {
    public static PictureSelectionModel setPhotoSelectOpt(PictureSelectionModel model, int count, double quality) {
        model
                .imageEngine(GlideEngine.createGlideEngine())
                .maxSelectNum(count)
                .minSelectNum(1)
                .maxVideoSelectNum(count)
                .minVideoSelectNum(1)
                .selectionMode(count > 1 ? PictureConfig.MULTIPLE : PictureConfig.SINGLE)
                .isSingleDirectReturn(false)
                .isWeChatStyle(true)
                .isCamera(false)
                .isZoomAnim(true)
                .isGif(true)
                .isEnableCrop(false)
                .isCompress(false)
                .minimumCompressSize(100)
                .isReturnEmpty(false)
                .isAndroidQTransform(true)
                .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .isPreviewImage(false)
                .isPreviewVideo(false)
                .isOriginalImageControl(false)
                .isMaxSelectEnabledMask(true)
                .setCameraImageFormat(PictureMimeType.JPEG)
                .setCameraVideoFormat(PictureMimeType.MP4)
                .renameCompressFile("image_picker_compress_"+UUID.randomUUID().toString()+".jpg")
                //.renameCropFileName("image_picker_crop_"+UUID.randomUUID().toString()+".jpg")
//                .cameraFileName("image_picker_camera_"+UUID.randomUUID().toString()+".jpg")
        ;
        if (quality > 0) {
            model.isCompress(true).compressQuality((int) ((double) quality * 100));
        }
        return model;
    }

    public static PictureSelectionModel setCropOpt(PictureSelectionModel model, HashMap<String, Object> opt,ArrayList<String> aspectRatioList) {
        model
                .isEnableCrop(true)
                .freeStyleCropMode(OverlayView.FREESTYLE_CROP_MODE_ENABLE)
                .circleDimmedLayer(opt.get("cropType").equals("CropType.circle"))
                .showCropFrame(!opt.get("cropType").equals("CropType.circle"))
                .showCropGrid(false)
                .rotateEnabled(true)
                .scaleEnabled(true)
                .isDragFrame(true)
                .hideBottomControls(false)
                .isMultipleSkipCrop(true)
                .cutOutQuality((int) ((double) opt.get("quality") * 100));
        if (opt.get("aspectRatioX") != null) {
            model.isDragFrame(false);
            model.withAspectRatio((int) opt.get("aspectRatioX"), (int) opt.get("aspectRatioY"));
        }
        if(aspectRatioList != null){
            UCrop.Options options = new UCrop.Options();
            options.setFreeStyleCropEnabled(false);
            model.isDragFrame(false);
            ArrayList<AspectRatio> aspectRatioArrayList = new ArrayList<>();
            for (int i = 0; i < aspectRatioList.size(); i++) {
                String preset = aspectRatioList.get(i);
                if (preset != null) {
                    AspectRatio aspectRatio = parseAspectRatio(preset);
                    aspectRatioArrayList.add(aspectRatio);
                }
            }
            options.setAspectRatioOptions(1, aspectRatioArrayList.toArray(new AspectRatio[]{}));
            model.basicUCropConfig(options);
        }
        return model;
    }

    public static PictureSelectionModel setLanguage(PictureSelectionModel model, String language) {
        switch (language) {
            case "Language.Chinese":
                model.setLanguage(LanguageConfig.CHINESE);
                break;
            case "Language.ChineseTraditional":
                model.setLanguage(LanguageConfig.TRADITIONAL_CHINESE);
                break;
            case "Language.English":
                model.setLanguage(LanguageConfig.ENGLISH);
                break;
            case "Language.Japanese":
                model.setLanguage(LanguageConfig.JAPAN);
                break;
            case "Language.French":
                model.setLanguage(LanguageConfig.FRANCE);
                break;
            case "Language.Korean":
                model.setLanguage(LanguageConfig.KOREA);
                break;
            case "Language.German":
                model.setLanguage(LanguageConfig.GERMANY);
                break;
            case "Language.Vietnamese":
                model.setLanguage(LanguageConfig.VIETNAM);
                break;
            default:
                model.setLanguage(-1);
        }
        return model;
    }


    private static AspectRatio parseAspectRatio(String name){
        if ("wh1x1".equals(name)) {
            return new AspectRatio(null, 1.0f, 1.0f);
        } else if ("original".equals(name)) {
            return new AspectRatio("ORIGINAL",
                    CropImageView.SOURCE_IMAGE_ASPECT_RATIO, 1.0f);
        } else if ("wh3x2".equals(name)) {
            return new AspectRatio(null, 3.0f, 2.0f);
        } else if ("wh4x3".equals(name)) {
            return new AspectRatio(null, 4.0f, 3.0f);
        } else if ("wh5x3".equals(name)) {
            return new AspectRatio(null, 5.0f, 3.0f);
        } else if ("wh5x4".equals(name)) {
            return new AspectRatio(null, 5.0f, 4.0f);
        } else if ("wh7x5".equals(name)) {
            return new AspectRatio(null, 7.0f, 5.0f);
        } else if ("wh16x9".equals(name)) {
            return new AspectRatio(null, 16.0f, 9.0f);
        } else {
            return new AspectRatio("ORIGINAL",
                    CropImageView.SOURCE_IMAGE_ASPECT_RATIO,  CropImageView.SOURCE_IMAGE_ASPECT_RATIO);
        }
    }
}
