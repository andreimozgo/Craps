package by.mozgo.craps.command;


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

/**
 * Contains methods to find or save user avatar
 *
 * @author Mozgo Andrei
 */
public class AvatarManager {
    private static final Logger LOG = LogManager.getLogger();
    private static final String PHOTO_FOLDER = "img\\avatars";
    private static final String PATTERN_PHOTO_EXTENSION = "(.png$)|(.jpg$)|(.jpeg$)";
    private static final String PHOTO_EXTENSION = ".png;.jpg";
    private static final String EXTENSION_DELIMITER = ";";
    private static final String DEFAULT_AVATAR = "noavatar.png";
    private static final String HEADER = "content-disposition";
    private static final String PARAMETER = "filename";
    private static final String PARAMS_DELIMITER = ";";
    private static final String VALUE_DELIMITER = ";";
    private static final String QUOTE = "\"";
    private ServletContext servletContext;

    public AvatarManager(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    /**
     * Finds extension of uploaded avatar file
     *
     * @param part
     * @return
     */
    private static String findFileExtension(Part part) {
        String partHeader = part.getHeader(HEADER);
        String fileName = "";

        // extracts file name
        for (String content : partHeader.split(PARAMS_DELIMITER)) {
            if (content.trim().startsWith(PARAMETER)) {
                fileName = content.substring(content.indexOf(VALUE_DELIMITER) + 1).trim().replace(QUOTE, "");
            }
        }
        // tries to find file supported extension
        Pattern pattern = Pattern.compile(PATTERN_PHOTO_EXTENSION);
        Matcher match = pattern.matcher(fileName.toLowerCase());
        if (match.find()) {
            return match.group();
        } else {
            return "";
        }
    }

    /**
     * Validates avatar file extension with preset pattern
     *
     * @param part
     * @return {@code true} if extension is valid, otherwise
     * {@code false}
     */
    public static boolean checkFileExtension(Part part) {
        String extension = findFileExtension(part);
        return !extension.isEmpty();
    }

    /**
     * Saves user avatar. If finds old avatar - deletes it
     *
     * @param part
     * @param userId
     */
    public void uploadAvatar(Part part, long userId) {
        String fileExtension = findFileExtension(part);
        String photoFilePath = servletContext.getRealPath("") + PHOTO_FOLDER + File.separator + userId;
        deleteAvatar(photoFilePath);
        try {
            part.write(photoFilePath + fileExtension);
        } catch (IOException e) {
            LOG.log(Level.ERROR, "Error while saving avatar: {}", e);
        }
    }

    /**
     * Deletes user avatar
     *
     * @param photoFilePath
     */
    private void deleteAvatar(String photoFilePath) {
        String[] extensionsArray = PHOTO_EXTENSION.split(EXTENSION_DELIMITER);
        List<String> extensions = new ArrayList<>(Arrays.asList(extensionsArray));
        for (String extension : extensions) {
            File photo = new File(photoFilePath + extension);
            if (photo.exists()) {
                photo.delete();
            }
        }
    }

    public String findPhotoRelativePath(long userId) {
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
