package by.mozgo.craps.manager;


import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AvatarManager {
    private static final String PHOTO_FOLDER = "img\\avatars";
    private static final String PATTERN_PHOTO_EXTENSION = "(.png$)|(.jpg$)";
    private static final String PHOTO_EXTENSION = ".png;.jpg";
    private static final String EXTENSION_DELIMITER = ";";
    private static final String DEFAULT_AVATAR = "noavatar.png";
    private static final String HEADER = "content-disposition";
    private static final String PARAMETER = "filename";
    private static final String PARAMS_DELIMITER = ";";
    private static final String VALUE_DELIMITER = ";";
    private static final String QUOTE = "\"";
    private static final Logger LOG = LogManager.getLogger();
    private ServletContext servletContext;

    public AvatarManager(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public void uploadPhoto(Part part, Integer userId) {
        String fileExtension = getFileExtension(part);

        if (!fileExtension.isEmpty()) {
            String photoFilePath = servletContext.getRealPath("") + PHOTO_FOLDER + File.separator + userId;
            deletePhoto(photoFilePath);
            try {
                part.write(photoFilePath + fileExtension);
            } catch (IOException e) {
                LOG.log(Level.ERROR, "Error while saving avatar: {}", e);
            }
        } else {
            LOG.log(Level.WARN, "Incorrect avatar extension.");
        }
    }

    /**
     * delete user's photo
     *
     * @param photoFilePath
     */
    private void deletePhoto(String photoFilePath) {
        String[] extensionsArray = PHOTO_EXTENSION.split(EXTENSION_DELIMITER);
        List<String> extensions = new ArrayList<>(Arrays.asList(extensionsArray));
        for (String extension : extensions) {
            File photo = new File(photoFilePath + extension);
            if (photo.exists()) {
                photo.delete();
            }
        }
    }


    private String getFileExtension(Part part) {
        String partHeader = part.getHeader(HEADER);
        String fileName = "";

        // extract file name
        for (String content : partHeader.split(PARAMS_DELIMITER)) {
            if (content.trim().startsWith(PARAMETER)) {
                fileName = content.substring(content.indexOf(VALUE_DELIMITER) + 1).trim().replace(QUOTE, "");
            }
        }
        // try to find file supported extension
        Pattern pattern = Pattern.compile(PATTERN_PHOTO_EXTENSION);
        Matcher match = pattern.matcher(fileName.toLowerCase());
        if (match.find()) {
            return match.group();
        } else {
            return "";
        }
    }

    public String findPhotoRelativePath(Integer userId) {
        String photoDirPath = servletContext.getRealPath("") + PHOTO_FOLDER + File.separator;
        String photoFilePath = null;
        String[] extensionsArray = PHOTO_EXTENSION.split(EXTENSION_DELIMITER);
        List<String> extensions = new ArrayList<>(Arrays.asList(extensionsArray));
        for (String extension : extensions) {
            File photo = new File(photoDirPath + userId + extension);
            if (photo.exists()) {
                photoFilePath = servletContext.getContextPath() + PHOTO_FOLDER + File.separator + userId + extension;
            }
        }

        if (photoFilePath == null) {
            photoFilePath = servletContext.getContextPath() + PHOTO_FOLDER + File.separator + DEFAULT_AVATAR;
        }

        return photoFilePath;
    }
}
