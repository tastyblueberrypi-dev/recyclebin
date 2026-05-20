package com.aainc.recyclebin.util;

import android.content.res.Resources;

import com.aainc.recyclebin.R;

// Holds list of message strings used in the app, also singleton
public class Str {

    // Global
    private static Str globalHandler;
    public static String ShellDirCmd = "ls";

    public String tra_alerttitle_terms;
    public String tra_alertbody_disclaimer;
    public String tra_alertbutton_accept;
    public String bin_str_started;
    public String bin_str_stopped;
    public String bin_app_name;
    public String bin_str_emptyfolder;
    public String bin_alerttitle_quickinstructions;
    public String tra_alertbutton_instructionvideo;
    public String bin_alertbody_quickinstructions;
    public String tra_alertbutton_okay;
    public String bin_str_restored;
    public String bin_str_deleted;
    public String bin_str_delete;
    public String bin_str_restore;
    public String bin_str_info;
    public String bin_alerttitle_delete;
    public String bin_alertbody_delete;
    public String tra_alertbutton_back;
    public String bin_str_datedeleted;

    public String urlvideo_aabin;

    public String tra_alerttitle_permission_all;
    // Predefined Reused Variables from Strings.xml
    public String tra_alertbody_photorecovery;
    public String tra_alertbody_filetransfer;
    public String tra_alerttitle_photorecovery;
    public String tra_alerttitle_ft;
    public String tra_alerttitle_quickinstructions;

    public String tra_alertbutton_ok;
    public String tra_alertbutton_openapp;
    public String tra_alertbutton_visitwebsite;
    public String tra_alertbutton_viewvideo;

    public String tra_alerttitle_videorecovery;
    public String tra_alerttitle_documentrecovery;
    public String tra_alerttitle_audiorecovery;
    public String tra_alertbody_videorecovery;
    public String tra_alertbody_documentrecovery;
    public String tra_alertbody_audiorecovery;
    public String tra_alertbutton_exit;
    public String tra_alertbody_fb;
    public String tra_alerttitle_fb;
    public String tra_alerttitle_finish;
    public String tra_alertbody_quickinstructions;
    public String tra_alerttitle_underdev;
    public String tra_alertbody_underdev;
    public String tra_alertbody_tra;
    public String tra_alerttitle_newsupdate;
    public String tra_app_name;
    public String tra_alerttitle_tra;
    public String tra_alerttitle_photorecyclebin;
    public String tra_alertbody_photorecyclebin;
    public String tra_alertbody_bin;
    public String tra_str_alertsecond;
    public String tra_alertbody_prsuccess;
    public String tra_alertbutton_nothanks;
    public String tra_str_mb;
    public String tra_str_kb;
    public String tra_str_rec;
    public String tra_alertbody_bin_video;
    public String tra_alerttitle_hider;
    public String tra_alertbody_hider;
    public String md_app_name;
    public String tra_alertbody_saver;
    public String tra_alerttitle_drbin;
    public String tra_alerttitle_arbin;
    public String tra_alertbody_drbin;
    public String tra_alertbody_arbin;
    public String tra_alerttitle_permission;
    public String tra_alertbody_permission;
    public String tra_alerttitle_nopermission;
    public String tra_alertbody_nopermission;
    public String tra_alertbody_bin_audio;
    public String tra_alertbody_bin_doc;
    public String tra_alertbutton_viewresults;

    // Photo Recovery related Strings from Strings.xml
    public String pr_alertbody_quickinstructions;
    public String pr_alertbody_success;
    public String pr_alertbutton_start;
    public String tra_alerttitle_alg1;
    public String pr_alerttitle_alg2;
    public String pr_alerttitle_finalinstructions;
    public String tra_alertbody_alg1;
    public String pr_alertbody_alg2;
    public String pr_alertbody_textads;
    public String pr_progressbarbody_alg1message;
    public String pr_progressbarbody_alg2message;
    public String pr_app_name;
    public String pr_alertbutton_viewresults;
    public String pr_alertbutton_transferresults;
    public String pr_alertbody_destfoldernotempty;
    public String pr_alerttitle_destfoldernotempty;
    public String pr_alertbody_destfolderempty;
    public String pr_alerttitle_destfolderempty;
    public String tra_alertbutton_continue;
    public String pr_alertbody_confirmdelete;
    public String tra_alertbutton_manage;
    public String dr_alerttitle_destfoldernotempty;
    public String dr_alertbody_destfoldernotempty;
    public String ar_alerttitle_destfoldernotempty;
    public String ar_alertbody_destfoldernotempty;
    public String dr_alerttitle_destfolderempty;
    public String dr_alertbody_destfolderempty;
    public String ar_alerttitle_destfolderempty;
    public String ar_alertbody_destfolderempty;
    public String dr_alertbody_success;
    public String ar_alertbody_success;
    public String dr_alertbutton_viewresults;
    public String ar_alertbutton_viewresults;
    public String video_gallery_confirm_delete;
    public String dr_alertbody_confirmdelete;
    public String ar_alertbody_confirmdelete;
    public String pr_sd_app_name;

    // File Transfer related Strings from Strings.xml
    public String fb_alertbody_quickinstructions;
    public String fb_alerttitle_confirmdelete;
    public String fb_alertbutton_delete;
    public String fb_str_inaccessible;
    public String fb_str_notadir;
    public String fb_str_alreadyathomedirectory;
    public String fb_app_name;
    public String fb_str_directory;
    public String fb_str_size;
    public String fb_str_date;
    public String fb_alertbutton_rename;
    public String fb_alertbody_rename;
    public String fb_progresstitle_deleting;
    public String fb_progressbody_deleting;
    public String fb_progresstitle_loadlist;
    public String fb_progressbody_loadlist;
    public String fb_alerttitle_properties;
    public String bin_video_gallery_viewresults;
    public String fb_str_storagefb;
    public String fb_str_rootfb;
    public String fb_str_sdcardfb;
    public String fb_alertbody_nosd;
    public String fb_alerttitle_checked;
    public String fb_alertbody_checked;
    public String fb_alerttitle_checkedempty;
    public String fb_alertbody_checkedempty;
    public String fb_alertbody_nochecked;
    public String fb_str_choosefb;
    public String fb_alerttitle_rootwarning;
    public String fb_alertbody_rootwarning;

    // File Transfer related Strings from Strings.xml
    public String ft_alerttitle_terms;
    public String ft_alerttitle_photorecovery;
    public String ft_alerttitle_fb;
    public String ft_alerttitle_quickinstructions;
    public String ft_alerttitle_nozippedfolder;
    public String ft_alerttitle_finish;
    public String ft_alerttitle_zippedfilesize;
    public String ft_alerttitle_selectphotozip;
    public String ft_alerttitle_sizewarning;
    public String ft_alerttitle_nozippedphoto;
    public String ft_alerttitle_waitlongtime;
    public String ft_alerttitle_loadlist;
    public String ft_alertbody_disclaimer;
    public String ft_alertbody_loadlist;
    public String ft_alertbody_waitlongtime;
    public String ft_alertbody_nozippedphoto;
    public String ft_alertbody_sizewarning;
    public String ft_alertbody_zipfirst;
    public String ft_alertbody_quickinstructions;
    public String ft_alertbody_photorecovery;
    public String ft_alertbody_fb;
    public String ft_alertbody_textads;
    public String ft_alertbody_sizeok;
    public String ft_alertbody_sizebig;
    public String ft_alertbody_chooseclient;
    public String ft_alertbutton_accept;
    public String ft_alertbutton_ok;
    public String ft_alertbutton_visitwebsite;
    public String ft_alertbutton_openapp;
    public String ft_alertbutton_viewvideo;
    public String ft_alertbutton_photorecovery;
    public String ft_progressbar_populating;
    public String ft_progressbar_zipping;
    public String ft_str_emailmessage;
    public String ft_app_name;
    public String ft_str_delimiter;
    public String ft_type_messagetype;
    public String ft_str_zipfilefolder;
    public String ft_str_zipfilename;

    // Recycle Bin related
    public String bin_str_recyclebin;
    public String bin_str_systeminternal;
    public String bin_photo_str_photo;
    public String bin_str_recyclebinfull;
    public String bin_photo_alertbody_running;
    public String bin_photo_alertbody_quickinstructions;
    public String bin_photo_app_name;
    public String bin_str_continuerun;
    public String bin_str_stoprun;
    public String bin_str_view;
    public String bin_str_empty;
    public String bin_alerttitle_empty;
    public String bin_alertbody_empty;
    public String bin_alertbutton_empty;
    public String bin_alerttitle_view;
    public String bin_alertbody_view;
    public String bin_alertbutton_view;
    public String bin_alertbutton_start;
    public String bin_alertbody_start;
    public String bin_alerttitle_start;
    public String bin_str_totalmem;
    public String bin_str_binmem;
    public String bin_photo_str_memmb;
    public String bin_str_allrecovered;
    public String bin_alerttitle_sizesmall;
    public String bin_video_alertbody_sizesmall;
    public String bin_photo_alertbody_sizesmall;
    public String bin_video_app_name;
    public String bin_video_alertbody_quickinstructions;
    public String bin_video_str_memmb;
    public String bin_video_str_video;
    public String bin_video_alertbody_running;
    public String bin_audio_app_name;
    public String bin_doc_app_name;
    public String bin_audio_str_memmb;
    public String bin_doc_str_memmb;
    public String bin_audio_alertbody_sizesmall;
    public String bin_doc_alertbody_sizesmall;
    public String bin_audio_alertbody_running;
    public String bin_doc_alertbody_running;
    public String bin_audio_str_audio;
    public String bin_doc_str_doc;
    public String bin_audio_alertbody_quickinstructions;
    public String bin_doc_alertbody_quickinstructions;
    public String bin_alertbody_binalreadyrunning;
    public String rec_alertbody_quickinstructions;
    public String bin_viewresults;

    // VR related
    public String vr_alertbody_quickinstructions;
    public String vr_app_name;
    public String vr_progressbarbody_alg1message;
    public String vr_recommend_body;
    public String vr_recommend_title;
    public String vr_alertbutton_viewresults;
    public String vr_alertbody_success;
    public String vr_alerttitle_destfoldernotempty;
    public String vr_alertbody_destfoldernotempty;
    public String vr_alerttitle_destfolderempty;
    public String vr_alertbody_destfolderempty;
    public String vr_alertbody_confirmdelete;
    public String dr_app_name;
    public String ar_app_name;
    public String ar_alertbody_quickinstructions;
    public String dr_alertbody_quickinstructions;
    public String pr_sd_alertbody_quickinstructions;

    // Static never requires change
    public String urlvideo_tra;
    public String urlvideo_pr;
    public String urlvideo_vr;
    public String urlvideo_dr;
    public String urlvideo_ar;
    public String urlvideo_saver;
    public String urlvideo_hider;
    public String url_website;
    public String url_saver;
    public String url_vr;
    public String url_dr;
    public String url_ar;
    public String url_drbin;
    public String url_arbin;
    public String url_pr;
    public String url_hider;
    public String url_tra;
    public String url_bin_photo;
    public String url_bin_video;
    public String urlvideo_bin_photo;
    public String urlvideo_bin_audio;
    public String urlvideo_bin_doc;
    public String urlvideo_bin_video;
    public String packagename_pr;
    public String packagename_vr;
    public String packagename_dr;
    public String packagename_ar;
    public String packagename_bin_doc;
    public String packagename_bin_audio;
    public String packagename_hider;
    public String packagename_saver;
    public String packagename_tra;
    public String packagename_bin_photo;
    public String packagename_bin_video;
    public String packagename_bin_photo_paid;
    public String packagename_tra_paid;

    // Public Toasted Error Messages
    public String tra_alertbody_error00;
    public String tra_alertbody_error01;
    public String tra_alertbody_error02;
    public String tra_alertbody_error03;
    public String tra_alertbody_error04;
    public String tra_alertbody_error05;
    public String tra_alertbody_error06;
    public String tra_alertbody_error07;
    public String tra_alertbody_error08;
    public String tra_alertbody_error09;
    public String tra_alertbody_error10;
    public String tra_alertbody_error11;
    public String tra_alertbody_error12;
    public String tra_alertbody_error13;
    public String tra_alertbody_error14;
    public String tra_alertbody_error15;
    public String tra_alertbody_error16;
    public String tra_alertbody_error17;
    public String tra_alertbody_error18;
    public String tra_alertbody_error19;
    public String tra_alertbody_error20;
    public String tra_alertbody_error21;
    public String tra_alertbody_error22;
    public String tra_alertbody_error23;
    public String tra_alertbody_error24;
    public String tra_alertbody_error25;
    public String tra_alertbody_error26;
    public String tra_alertbody_error27;
    public String tra_alertbody_error28;
    public String tra_alertbody_error29;
    public String tra_alertbody_error30;
    public String tra_alertbody_error31;
    public String tra_alertbody_error32;
    public String tra_alertbody_error33;
    public String tra_alertbody_error34;
    public String tra_alertbody_error35;
    public String tra_alertbody_error36;
    public String tra_alertbody_error37;
    public String tra_alertbody_error38;
    public String tra_alertbody_error39;
    public String tra_alertbody_error40;

    // Currently not used
    public String url_testerapplist;

    // Constructor will initializes variables
    public Str() {

    }

    public static synchronized Str getSing() {
        if (globalHandler == null)
            globalHandler = new Str();

        return globalHandler;
    }

    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    // Initializes variables
    public void initializeVar(Resources resources) {
        // Global Related
        tra_alertbody_disclaimer = (String) resources
                .getText(R.string.tra_alertbody_disclaimer);
        tra_alerttitle_terms = (String) resources
                .getText(R.string.tra_alerttitle_terms);
        tra_alertbutton_accept = (String) resources
                .getText(R.string.tra_alertbutton_accept);
        bin_str_started = (String) resources
                .getText(R.string.bin_str_started);
        bin_str_stopped = (String) resources
                .getText(R.string.bin_str_stopped);
        bin_app_name = (String) resources
                .getText(R.string.bin_app_name);
        bin_str_emptyfolder = (String) resources
                .getText(R.string.bin_str_emptyfolder);
        bin_alerttitle_quickinstructions = (String) resources
                .getText(R.string.bin_alerttitle_quickinstructions);
        bin_alertbody_quickinstructions = (String) resources
                .getText(R.string.bin_alertbody_quickinstructions);
        tra_alertbutton_okay = (String) resources
                .getText(R.string.tra_alertbutton_okay);
        bin_str_restored = (String) resources
                .getText(R.string.bin_str_restored);
        bin_str_deleted = (String) resources
                .getText(R.string.bin_str_deleted);
        bin_str_delete= (String) resources
                .getText(R.string.bin_str_delete);
        bin_str_restore= (String) resources
                .getText(R.string.bin_str_restore);
        bin_str_info= (String) resources
                .getText(R.string.bin_str_info);
        bin_alerttitle_delete= (String) resources
                .getText(R.string.bin_alerttitle_delete);
        bin_alertbody_delete= (String) resources
                .getText(R.string.bin_alertbody_delete);
        tra_alertbutton_back= (String) resources
                .getText(R.string.tra_alertbutton_back);
        bin_str_datedeleted = (String) resources
                .getText(R.string.bin_str_datedeleted);
        tra_alertbutton_instructionvideo = (String) resources
                .getText(R.string.tra_alertbutton_instructionvideo);

        urlvideo_aabin= (String) resources
                .getText(R.string.urlvideo_aabin);

        tra_alerttitle_permission_all= (String) resources
                .getText(R.string.tra_alerttitle_permission_all);
        tra_alertbody_permission = (String) resources
                .getText(R.string.tra_alertbody_permission);
        /*tra_alertbody_photorecovery = (String) resources
				.getText(R.string.tra_alertbody_photorecovery);
		tra_alertbody_filetransfer = (String) resources
				.getText(R.string.tra_alertbody_filetransfer);

		tra_alertbutton_openapp = (String) resources
				.getText(R.string.tra_alertbutton_openapp);
		tra_alerttitle_photorecovery = (String) resources
				.getText(R.string.tra_alerttitle_photorecovery);
		tra_alerttitle_ft = (String) resources
				.getText(R.string.tra_alerttitle_ft);
		tra_alerttitle_quickinstructions = (String) resources
				.getText(R.string.tra_alerttitle_quickinstructions);
		tra_alertbutton_visitwebsite = (String) resources
				.getText(R.string.tra_alertbutton_visitwebsite);
		tra_alertbutton_viewvideo = (String) resources
				.getText(R.string.tra_alertbutton_viewvideo);
		tra_alerttitle_videorecovery = (String) resources
				.getText(R.string.tra_alerttitle_viderecovery);
		tra_alerttitle_documentrecovery = (String) resources
				.getText(R.string.dr_app_name);
		tra_alerttitle_audiorecovery = (String) resources
				.getText(R.string.ar_app_name);
		tra_alertbody_videorecovery = (String) resources
				.getText(R.string.tra_alertbody_videorecovery);
		tra_alertbody_documentrecovery = (String) resources
				.getText(R.string.tra_alertbody_documentrecovery);
		tra_alertbody_audiorecovery = (String) resources
				.getText(R.string.tra_alertbody_audiorecovery);
		tra_alerttitle_quickinstructions = (String) resources
				.getText(R.string.tra_alerttitle_quickinstructions);
		tra_alertbutton_exit = (String) resources
				.getText(R.string.tra_alertbutton_exit);
		tra_alertbody_fb = (String) resources
				.getText(R.string.tra_alertbody_filebrowser);
		tra_alerttitle_fb = (String) resources
				.getText(R.string.tra_alerttitle_fb);
		tra_alerttitle_finish = (String) resources
				.getText(R.string.tra_alerttitle_finish);
		tra_alertbody_quickinstructions = (String) resources
				.getText(R.string.tra_alertbody_quickinstructions);
		tra_alerttitle_underdev = (String) resources
				.getText(R.string.tra_alerttitle_underdev);
		tra_alertbody_underdev = (String) resources
				.getText(R.string.tra_alertbody_underdev);
		tra_alertbody_tra = (String) resources
				.getText(R.string.tra_alertbody_tra);
		tra_alerttitle_newsupdate = (String) resources
				.getText(R.string.tra_alerttitle_newsupdate);
		tra_app_name = (String) resources.getText(R.string.tra_app_name);
		tra_alerttitle_tra = (String) resources
				.getText(R.string.tra_alerttitle_tra);
		tra_alerttitle_photorecyclebin = (String) resources
				.getText(R.string.tra_alerttitle_photorecyclebin);
		tra_alertbody_photorecyclebin = (String) resources
				.getText(R.string.tra_alertbody_photorecyclebin);
		tra_alertbody_bin = (String) resources
				.getText(R.string.tra_alertbody_bin);
		tra_str_alertsecond = (String) resources
				.getText(R.string.tra_str_alertsecond);
		tra_alertbutton_back = (String) resources
				.getText(R.string.tra_alertbutton_back);
		tra_alertbutton_nothanks = (String) resources
				.getText(R.string.tra_alertbutton_nothanks);
		tra_str_rec= (String) resources
				.getText(R.string.tra_str_rec);
		tra_alertbody_bin_video= (String) resources
				.getText(R.string.tra_alertbody_bin_video);
		tra_alerttitle_hider= (String) resources
				.getText(R.string.tra_alerttitle_hider);
		tra_alertbody_hider= (String) resources
				.getText(R.string.tra_alertbody_hider);
		md_app_name = (String) resources
				.getText(R.string.md_app_name);
		tra_alertbody_saver= (String) resources
				.getText(R.string.tra_alertbody_saver);
		tra_alerttitle_drbin = (String) resources
				.getText(R.string.tra_alerttitle_drbin);
		tra_alerttitle_arbin = (String) resources
				.getText(R.string.tra_alerttitle_arbin);
		tra_alertbody_drbin = (String) resources
				.getText(R.string.tra_alertbody_drbin);
		tra_alertbody_arbin = (String) resources
				.getText(R.string.tra_alertbody_arbin);
		tra_alerttitle_permission = (String) resources
				.getText(R.string.tra_alerttitle_permission);
		tra_alertbody_permission = (String) resources
				.getText(R.string.tra_alertbody_permission);
		tra_alerttitle_nopermission = (String) resources
				.getText(R.string.tra_alerttitle_nopermission);
		tra_alertbody_nopermission = (String) resources
				.getText(R.string.tra_alertbody_nopermission);
		tra_alertbody_bin_doc= (String) resources
				.getText(R.string.tra_alertbody_bin_doc);
		tra_alertbody_bin_audio= (String) resources
				.getText(R.string.tra_alertbody_bin_audio);
		tra_alertbutton_viewresults = (String) resources
				.getText(R.string.tra_alertbutton_viewresults);

		// Global Related
		pr_alertbutton_start = (String) resources
				.getText(R.string.pr_alertbutton_start);
		pr_progressbarbody_alg1message = (String) resources
				.getText(R.string.pr_progressbarbody_alg1message);
		pr_progressbarbody_alg2message = (String) resources
				.getText(R.string.pr_progressbarbody_alg2message);
		tra_alerttitle_alg1 = (String) resources
				.getText(R.string.tra_alerttitle_alg1);
		pr_alerttitle_alg2 = (String) resources
				.getText(R.string.pr_alerttitle_alg2);
		pr_alerttitle_finalinstructions = (String) resources
				.getText(R.string.pr_alerttitle_finalinstructions);
		tra_alertbody_alg1 = (String) resources
				.getText(R.string.tra_alertbody_alg1);
		pr_alertbody_alg2 = (String) resources
				.getText(R.string.pr_alertbody_alg2);
		pr_alertbody_textads = (String) resources
				.getText(R.string.pr_alertbody_textads);
		pr_alertbody_quickinstructions = (String) resources
				.getText(R.string.pr_alertbody_quickinstructions);
		pr_alertbody_success = (String) resources
				.getText(R.string.pr_alertbody_success);
		pr_app_name = (String) resources.getText(R.string.pr_app_name);
		pr_alertbutton_transferresults = (String) resources
				.getText(R.string.pr_alertbutton_transferresults);
		pr_alertbutton_viewresults = (String) resources
				.getText(R.string.pr_alertbutton_viewresults);
		pr_alertbody_destfoldernotempty = (String) resources
				.getText(R.string.pr_alertbody_destfoldernotempty);
		pr_alerttitle_destfoldernotempty = (String) resources
				.getText(R.string.pr_alerttitle_destfoldernotempty);
		pr_alertbody_destfolderempty = (String) resources
				.getText(R.string.pr_alertbody_destfolderempty);
		pr_alerttitle_destfolderempty = (String) resources
				.getText(R.string.pr_alerttitle_destfolderempty);
		tra_alertbutton_continue = (String) resources
				.getText(R.string.tra_alertbutton_continue);
		pr_alertbody_confirmdelete = (String) resources
				.getText(R.string.pr_alertbody_confirmdelete);
		tra_alertbutton_manage = (String) resources
				.getText(R.string.tra_alertbutton_manage);
		dr_alerttitle_destfoldernotempty = (String) resources
				.getText(R.string.dr_alerttitle_destfoldernotempty);
		dr_alertbody_destfoldernotempty= (String) resources
				.getText(R.string.dr_alertbody_destfoldernotempty);
		ar_alerttitle_destfoldernotempty= (String) resources
				.getText(R.string.ar_alerttitle_destfoldernotempty);
		ar_alertbody_destfoldernotempty= (String) resources
				.getText(R.string.ar_alertbody_destfoldernotempty);
		dr_alerttitle_destfolderempty= (String) resources
				.getText(R.string.dr_alerttitle_destfolderempty);
		dr_alertbody_destfolderempty= (String) resources
				.getText(R.string.dr_alertbody_destfolderempty);
		ar_alerttitle_destfolderempty= (String) resources
				.getText(R.string.ar_alerttitle_destfolderempty);
		ar_alertbody_destfolderempty= (String) resources
				.getText(R.string.ar_alertbody_destfolderempty);
		dr_alertbody_success= (String) resources
				.getText(R.string.dr_alertbody_success);
		ar_alertbody_success= (String) resources
				.getText(R.string.ar_alertbody_success);
		dr_alertbutton_viewresults= (String) resources
				.getText(R.string.dr_alertbutton_viewresults);
		ar_alertbutton_viewresults= (String) resources
				.getText(R.string.ar_alertbutton_viewresults);
		video_gallery_confirm_delete= (String) resources
				.getText(R.string.video_gallery_confirm_delete);
		dr_alertbody_confirmdelete= (String) resources
				.getText(R.string.dr_alertbody_confirmdelete);
		ar_alertbody_confirmdelete= (String) resources
				.getText(R.string.ar_alertbody_confirmdelete);
		pr_sd_app_name= (String) resources
				.getText(R.string.pr_sd_app_name);

		// Global Related
		fb_alertbody_quickinstructions = (String) resources
				.getText(R.string.fb_alertbody_quickinstructions);
		fb_str_inaccessible = (String) resources
				.getText(R.string.fb_str_inaccessible);
		fb_str_notadir = (String) resources.getText(R.string.fb_str_notadir);
		fb_alerttitle_confirmdelete = (String) resources
				.getText(R.string.fb_alerttitle_confirmdelete);
		fb_alertbutton_delete = (String) resources
				.getText(R.string.fb_alertbutton_delete);
		fb_str_alreadyathomedirectory = (String) resources
				.getText(R.string.fb_str_alreadyathomedirectory);
		fb_app_name = (String) resources.getText(R.string.fb_app_name);
		fb_str_directory = (String) resources
				.getText(R.string.fb_str_directory);
		fb_alerttitle_properties = (String) resources
				.getText(R.string.fb_alerttitle_properties);
		fb_progresstitle_deleting = (String) resources
				.getText(R.string.fb_progresstitle_deleting);
		fb_progressbody_deleting = (String) resources
				.getText(R.string.fb_progressbody_deleting);
		fb_progresstitle_loadlist = (String) resources
				.getText(R.string.fb_progresstitle_loadlist);
		fb_progressbody_loadlist = (String) resources
				.getText(R.string.fb_progressbody_loadlist);
		fb_str_size = (String) resources.getText(R.string.fb_str_size);
		fb_str_date = (String) resources.getText(R.string.fb_str_date);
		fb_alertbutton_rename = (String) resources
				.getText(R.string.fb_alertbutton_rename);
		fb_alertbody_rename = (String) resources
				.getText(R.string.fb_alertbody_rename);
		bin_video_gallery_viewresults = (String) resources
				.getText(R.string.bin_video_galley_viewresults);
		fb_str_storagefb = (String) resources
				.getText(R.string.fb_str_storagefb);
		fb_str_rootfb = (String) resources
				.getText(R.string.fb_str_rootfb);
		fb_str_sdcardfb = (String) resources
				.getText(R.string.fb_str_sdcardfb);
        fb_alertbody_nosd= (String) resources
                .getText(R.string.fb_alertbody_nosd);
		fb_alerttitle_checked = (String) resources
				.getText(R.string.fb_alerttitle_checked);
		fb_alertbody_checked = (String) resources
				.getText(R.string.fb_alertbody_checked);
		fb_alerttitle_checkedempty = (String) resources
				.getText(R.string.fb_alerttitle_checkempty);
		fb_alertbody_checkedempty = (String) resources
				.getText(R.string.fb_alertbody_checkempty);
		fb_alertbody_nochecked = (String) resources
				.getText(R.string.fb_alertbody_nochecked);
		fb_str_choosefb = (String) resources
				.getText(R.string.fb_str_choosefb);
		fb_alerttitle_rootwarning = (String) resources
				.getText(R.string.fb_alerttitle_rootwarning);
		fb_alertbody_rootwarning = (String) resources
				.getText(R.string.fb_alertbody_rootwarning);

		// Global Related
		ft_alerttitle_terms = (String) resources
				.getText(R.string.tra_alerttitle_terms);
		ft_alertbutton_accept = (String) resources
				.getText(R.string.tra_alertbutton_accept);
		ft_alertbody_disclaimer = (String) resources
				.getText(R.string.tra_alertbody_disclaimer);
		ft_alertbody_quickinstructions = (String) resources
				.getText(R.string.ft_alertbody_quickinstructions);
		ft_alertbody_photorecovery = (String) resources
				.getText(R.string.tra_alertbody_photorecovery);
		ft_alertbody_fb = (String) resources
				.getText(R.string.tra_alertbody_filebrowser);
		ft_alerttitle_photorecovery = (String) resources
				.getText(R.string.tra_alerttitle_photorecovery);
		ft_alerttitle_fb = (String) resources
				.getText(R.string.tra_alerttitle_fb);
		ft_alerttitle_quickinstructions = (String) resources
				.getText(R.string.tra_alerttitle_quickinstructions);
		ft_alerttitle_nozippedfolder = (String) resources
				.getText(R.string.ft_alerttitle_nozippedfolder);
		ft_alerttitle_finish = (String) resources
				.getText(R.string.tra_alerttitle_finish);
		ft_alertbody_textads = (String) resources
				.getText(R.string.ft_alertbody_textads);
		ft_alerttitle_zippedfilesize = (String) resources
				.getText(R.string.ft_alerttitle_zippedfilesize);
		ft_alertbutton_ok = (String) resources
				.getText(R.string.tra_alertbutton_ok);
		ft_alertbody_sizeok = (String) resources
				.getText(R.string.ft_alertbody_sizeok);
		ft_alertbody_sizebig = (String) resources
				.getText(R.string.ft_alertbody_sizebig);
		ft_alerttitle_selectphotozip = (String) resources
				.getText(R.string.ft_alerttitle_selectphotozip);
		ft_alertbody_zipfirst = (String) resources
				.getText(R.string.ft_alertbody_zipfirst);
		ft_alertbutton_photorecovery = (String) resources
				.getText(R.string.ft_alertbutton_downloadphotorecovery);
		ft_alerttitle_sizewarning = (String) resources
				.getText(R.string.ft_alerttitle_sizewarning);
		ft_alertbody_sizewarning = (String) resources
				.getText(R.string.ft_alertbody_sizewarning);
		ft_alerttitle_nozippedphoto = (String) resources
				.getText(R.string.ft_alerttitle_nozippedphoto);
		ft_alertbody_nozippedphoto = (String) resources
				.getText(R.string.ft_alertbody_nozippedphoto);
		ft_progressbar_zipping = (String) resources
				.getText(R.string.ft_progressbar_zipping);
		ft_alertbody_chooseclient = (String) resources
				.getText(R.string.ft_alertbody_chooseclient);
		ft_str_emailmessage = (String) resources
				.getText(R.string.ft_str_emailmessage);
		ft_alertbutton_visitwebsite = (String) resources
				.getText(R.string.tra_alertbutton_visitwebsite);
		ft_alertbutton_openapp = (String) resources
				.getText(R.string.tra_alertbutton_openapp);
		ft_alerttitle_waitlongtime = (String) resources
				.getText(R.string.ft_alerttitle_waitlongtime);
		ft_alertbody_waitlongtime = (String) resources
				.getText(R.string.ft_alertbody_waitlongtime);
		ft_progressbar_populating = (String) resources
				.getText(R.string.ft_progressbar_populating);
		ft_alerttitle_loadlist = (String) resources
				.getText(R.string.ft_alerttitle_loadlist);
		ft_alertbody_loadlist = (String) resources
				.getText(R.string.ft_alertbody_loadlist);
		ft_alertbutton_viewvideo = (String) resources
				.getText(R.string.tra_alertbutton_viewvideo);
		ft_app_name = (String) resources.getText(R.string.ft_app_name);
		tra_str_mb = (String) resources.getText(R.string.tra_str_mb);
		tra_str_kb = (String) resources.getText(R.string.tra_str_kb);
		ft_str_delimiter = (String) resources.getText(R.string.ft_str_delim);
		ft_str_zipfilefolder = (String) resources
				.getText(R.string.ft_str_zipfilefolder);
		ft_str_zipfilename = (String) resources
				.getText(R.string.ft_str_zipfilename);
		ft_type_messagetype = "message/rfc822";

		// Recycle Bin related
		bin_str_recyclebin = (String) resources
				.getText(R.string.bin_str_recyclebin);
		bin_str_systeminternal = (String) resources
				.getText(R.string.bin_str_systeminternal);
		bin_photo_str_photo = (String) resources.getText(R.string.bin_photo_str_photo);
		bin_str_recyclebinfull = (String) resources
				.getText(R.string.bin_str_recyclebinfull);
		bin_photo_alertbody_running = (String) resources
				.getText(R.string.bin_photo_alertbody_running);
		bin_video_alertbody_running = (String) resources
				.getText(R.string.bin_video_alertbody_running);
		bin_photo_alertbody_quickinstructions = (String) resources
				.getText(R.string.bin_photo_alertbody_quickinstructions);
		bin_photo_app_name = (String) resources.getText(R.string.bin_photo_app_name);
		bin_str_continuerun = (String) resources
				.getText(R.string.bin_str_continuerun);
		bin_str_stoprun = (String) resources.getText(R.string.bin_str_stoprun);
		bin_str_view = (String) resources.getText(R.string.bin_str_view);
		bin_str_empty = (String) resources.getText(R.string.bin_str_empty);
		bin_alerttitle_empty = (String) resources
				.getText(R.string.bin_alerttitle_empty);
		bin_alertbody_empty = (String) resources
				.getText(R.string.bin_alertbody_empty);
		bin_alertbutton_empty = (String) resources
				.getText(R.string.bin_alertbutton_empty);
		bin_alerttitle_view = (String) resources
				.getText(R.string.bin_alerttitle_view);
		bin_alertbody_view = (String) resources
				.getText(R.string.bin_alertbody_view);
		bin_alertbutton_view = (String) resources
				.getText(R.string.bin_alertbutton_view);
		bin_alerttitle_start = (String) resources
				.getText(R.string.bin_alerttitle_start);
		bin_alertbody_start = (String) resources
				.getText(R.string.bin_alertbody_start);
		bin_alertbutton_start = (String) resources
				.getText(R.string.bin_alertbutton_start);
		bin_str_binmem = (String) resources.getText(R.string.bin_str_binmem);
		bin_str_totalmem = (String) resources
				.getText(R.string.bin_str_totalmem);
		bin_photo_str_memmb = (String) resources
				.getText(R.string.bin_photo_str_memmb);
		bin_str_allrecovered = (String) resources
				.getText(R.string.bin_str_allrecovered);
		bin_alerttitle_sizesmall = (String) resources
				.getText(R.string.bin_alerttitle_sizesmall);
		bin_video_alertbody_sizesmall = (String) resources
				.getText(R.string.bin_video_alertbody_sizesmall);
		bin_photo_alertbody_sizesmall = (String) resources
				.getText(R.string.bin_photo_alertbody_sizesmall);
		bin_video_app_name= (String) resources
				.getText(R.string.bin_video_app_name);
		bin_video_alertbody_quickinstructions= (String) resources
				.getText(R.string.bin_video_alertbody_quickinstructions);
		bin_video_str_memmb= (String) resources
				.getText(R.string.bin_video_str_memmb);
		bin_video_str_video= (String) resources
				.getText(R.string.bin_video_str_video);
		bin_audio_app_name = (String) resources
				.getText(R.string.bin_audio_app_name);
		bin_doc_app_name= (String) resources
				.getText(R.string.bin_doc_app_name);
		bin_audio_str_memmb= (String) resources
				.getText(R.string.bin_audio_str_memmb);
		bin_doc_str_memmb= (String) resources
				.getText(R.string.bin_doc_str_memmb);
		bin_audio_alertbody_sizesmall= (String) resources
				.getText(R.string.bin_audio_alertbody_sizesmall);
		bin_doc_alertbody_sizesmall= (String) resources
				.getText(R.string.bin_doc_alertbody_sizesmall);
		bin_audio_alertbody_running= (String) resources
				.getText(R.string.bin_audio_alertbody_running);
		bin_doc_alertbody_running= (String) resources
				.getText(R.string.bin_doc_alertbody_running);
		bin_audio_str_audio= (String) resources
				.getText(R.string.bin_audio_str_audio);
		bin_doc_str_doc= (String) resources
				.getText(R.string.bin_doc_str_doc);
		bin_audio_alertbody_quickinstructions  = (String) resources
				.getText(R.string.bin_audio_alertbody_quickinstructions);
		bin_doc_alertbody_quickinstructions  = (String) resources
				.getText(R.string.bin_doc_alertbody_quickinstructions);
		bin_alertbody_binalreadyrunning = (String) resources
				.getText(R.string.bin_alertbody_binalreadyrunning);
		bin_viewresults= (String) resources
				.getText(R.string.bin_viewresults);

		//VR related
		vr_alertbody_quickinstructions = (String) resources
				.getText(R.string.vr_alertbody_quickinstructions);
		vr_app_name = (String) resources
				.getText(R.string.vr_app_name);
		vr_progressbarbody_alg1message = (String) resources
				.getText(R.string.vr_progressbarbody_alg1message);
		vr_recommend_body = (String) resources
				.getText(R.string.vr_recommend_body);
		vr_recommend_title = (String) resources
				.getText(R.string.vr_recommend_title);
		rec_alertbody_quickinstructions= (String) resources
				.getText(R.string.rec_alertbody_quickinstructions);
		vr_alertbutton_viewresults = (String) resources
				.getText(R.string.vr_alertbutton_viewresults);
        vr_alertbody_success = (String) resources
                .getText(R.string.vr_alertbody_success);
        vr_alerttitle_destfoldernotempty = (String) resources
                .getText(R.string.vr_alerttitle_destfoldernotempty);
        vr_alertbody_destfoldernotempty= (String) resources
                .getText(R.string.vr_alertbody_destfoldernotempty);
        vr_alerttitle_destfolderempty= (String) resources
                .getText(R.string.vr_alerttitle_destfolderempty);
        vr_alertbody_destfolderempty= (String) resources
                .getText(R.string.vr_alertbody_destfolderempty);
        vr_alertbody_confirmdelete= (String) resources
                .getText(R.string.vr_alertbody_confirmdelete);
		ar_app_name= (String) resources
				.getText(R.string.ar_app_name);
		dr_app_name= (String) resources
				.getText(R.string.dr_app_name);
		ar_alertbody_quickinstructions = (String) resources
				.getText(R.string.ar_alertbody_quickinstructions);
		dr_alertbody_quickinstructions = (String) resources
				.getText(R.string.dr_alertbody_quickinstructions);
		pr_sd_alertbody_quickinstructions = (String) resources
				.getText(R.string.pr_sd_alertbody_quickinstructions);

		// Static never requires change
		urlvideo_pr = (String) resources.getText(R.string.urlvideo_pr);
		urlvideo_vr = (String) resources
				.getText(R.string.urlvideo_videorecovery);
		urlvideo_dr = (String) resources
				.getText(R.string.urlvideo_documentrecovery);
		urlvideo_ar = (String) resources
				.getText(R.string.urlvideo_audiorecovery);
		urlvideo_saver = (String) resources.getText(R.string.urlvideo_saver);
		urlvideo_hider = (String) resources.getText(R.string.urlvideo_hider);
		urlvideo_bin_photo = (String) resources.getText(R.string.urlvideo_bin_photo);
		urlvideo_bin_audio = (String) resources.getText(R.string.urlvideo_bin_audio);
		urlvideo_bin_doc = (String) resources.getText(R.string.urlvideo_bin_doc);
		urlvideo_bin_video = (String) resources.getText(R.string.urlvideo_bin_video);
		packagename_pr = (String) resources.getText(R.string.packagename_pr);
		packagename_vr = (String) resources.getText(R.string.packagename_vr);
		packagename_dr = (String) resources.getText(R.string.packagename_dr);
		packagename_ar = (String) resources.getText(R.string.packagename_ar);
		packagename_bin_doc = (String) resources.getText(R.string.packagename_bin_doc);
		packagename_bin_audio = (String) resources.getText(R.string.packagename_bin_audio);
		packagename_saver = (String) resources.getText(R.string.packagename_saver);
		packagename_hider = (String) resources.getText(R.string.packagename_hider);
		packagename_tra = (String) resources.getText(R.string.packagename_tra);
		packagename_bin_photo = (String) resources.getText(R.string.packagename_bin_photo);
		packagename_bin_photo_paid = (String) resources.getText(R.string.packagename_bin_photo_paid);
		packagename_tra_paid = (String) resources.getText(R.string.packagename_tra_paid);
		packagename_bin_video = (String) resources.getText(R.string.packagename_bin_video);
		urlvideo_tra = (String) resources.getText(R.string.urlvideo_tra);
		url_pr = (String) resources.getText(R.string.url_pr);
		url_vr = (String) resources.getText(R.string.url_vr);
		url_dr = (String) resources.getText(R.string.url_dr);
		url_ar = (String) resources.getText(R.string.url_ar);
		url_drbin = (String) resources.getText(R.string.url_bin_dr);
		url_arbin = (String) resources.getText(R.string.url_bin_ar);
		url_saver = (String) resources.getText(R.string.url_saver);
		url_hider = (String) resources.getText(R.string.url_hider);
		url_website = (String) resources.getText(R.string.url_website);
		url_tra = (String) resources.getText(R.string.url_tra);
		url_bin_photo = (String) resources.getText(R.string.url_bin_photo);
		url_bin_video = (String) resources.getText(R.string.url_bin_video);

		// Error Messages
		tra_alertbody_error00 = (String) resources
				.getText(R.string.tra_alertbody_error00);
		tra_alertbody_error01 = (String) resources
				.getText(R.string.tra_alertbody_error01);
		tra_alertbody_error02 = (String) resources
				.getText(R.string.tra_alertbody_error02);
		tra_alertbody_error03 = (String) resources
				.getText(R.string.tra_alertbody_error03);
		tra_alertbody_error04 = (String) resources
				.getText(R.string.tra_alertbody_error04);
		tra_alertbody_error05 = (String) resources
				.getText(R.string.tra_alertbody_error05);
		tra_alertbody_error06 = (String) resources
				.getText(R.string.tra_alertbody_error06);
		tra_alertbody_error07 = (String) resources
				.getText(R.string.tra_alertbody_error07);
		tra_alertbody_error08 = (String) resources
				.getText(R.string.tra_alertbody_error08);
		tra_alertbody_error09 = (String) resources
				.getText(R.string.tra_alertbody_error09);
		tra_alertbody_error10 = (String) resources
				.getText(R.string.tra_alertbody_error10);
		tra_alertbody_error11 = (String) resources
				.getText(R.string.tra_alertbody_error11);
		tra_alertbody_error12 = (String) resources
				.getText(R.string.tra_alertbody_error12);
		tra_alertbody_error13 = (String) resources
				.getText(R.string.tra_alertbody_error13);
		tra_alertbody_error14 = (String) resources
				.getText(R.string.tra_alertbody_error14);
		tra_alertbody_error15 = (String) resources
				.getText(R.string.tra_alertbody_error15);
		tra_alertbody_error16 = (String) resources
				.getText(R.string.tra_alertbody_error16);
		tra_alertbody_error17 = (String) resources
				.getText(R.string.tra_alertbody_error17);
		tra_alertbody_error18 = (String) resources
				.getText(R.string.tra_alertbody_error18);
		tra_alertbody_error19 = (String) resources
				.getText(R.string.tra_alertbody_error19);
		tra_alertbody_error20 = (String) resources
				.getText(R.string.tra_alertbody_error20);
		tra_alertbody_error21 = (String) resources
				.getText(R.string.tra_alertbody_error21);
		tra_alertbody_error22 = (String) resources
				.getText(R.string.tra_alertbody_error22);
		tra_alertbody_error23 = (String) resources
				.getText(R.string.tra_alertbody_error23);
		tra_alertbody_error24 = (String) resources
				.getText(R.string.tra_alertbody_error24);
		tra_alertbody_error25 = (String) resources
				.getText(R.string.tra_alertbody_error25);
		tra_alertbody_error26 = (String) resources
				.getText(R.string.tra_alertbody_error26);
		tra_alertbody_error27 = (String) resources
				.getText(R.string.tra_alertbody_error27);
		tra_alertbody_error28 = (String) resources
				.getText(R.string.tra_alertbody_error28);
		tra_alertbody_error29 = (String) resources
				.getText(R.string.tra_alertbody_error29);
		tra_alertbody_error30 = (String) resources
				.getText(R.string.tra_alertbody_error30);
		tra_alertbody_error31 = (String) resources
				.getText(R.string.tra_alertbody_error31);
		tra_alertbody_error32 = (String) resources
				.getText(R.string.tra_alertbody_error32);
		tra_alertbody_error33 = (String) resources
				.getText(R.string.tra_alertbody_error33);
		tra_alertbody_error34 = (String) resources
				.getText(R.string.tra_alertbody_error34);
		tra_alertbody_error35 = (String) resources
				.getText(R.string.tra_alertbody_error35);
		tra_alertbody_error36 = (String) resources
				.getText(R.string.tra_alertbody_error36);
		tra_alertbody_error37 = (String) resources
				.getText(R.string.tra_alertbody_error37);
		tra_alertbody_error38 = (String) resources
				.getText(R.string.tra_alertbody_error38);
		tra_alertbody_error39 = (String) resources
				.getText(R.string.tra_alertbody_error39);
		tra_alertbody_error39 = (String) resources
				.getText(R.string.tra_alertbody_error40);

		// Currently not used
		url_testerapplist = (String) resources
				.getText(R.string.url_testerapplist);*/
    }
}
