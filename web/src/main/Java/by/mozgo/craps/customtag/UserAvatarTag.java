package by.mozgo.craps.customtag;

import by.mozgo.craps.command.AvatarManager;
import by.mozgo.craps.entity.User;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * Custom tag to define user avatar path and output it as &lt;img&gt; tag
 *
 * @author Mozgo Andrei
 */
public class UserAvatarTag extends TagSupport {
    private static final long serialVersionUID = 1L;

    private User user;

    @Override
    public int doEndTag() throws JspException {
        try {
            /* reset params for enable proper reuse */
            user = null;
            JspWriter out = pageContext.getOut();
            out.write(">");
        } catch (IOException e) {
            throw new JspTagException(e.getMessage());
        }
        return EVAL_PAGE;
    }

    @Override
    public int doStartTag() throws JspException {
        AvatarManager avatarManager = new AvatarManager(pageContext.getServletContext());
        try {
            JspWriter out = pageContext.getOut();
            out.write("<img src='");
			/* if user param isn't defined - take user from session */
            if (user == null) {
                user = (User) pageContext.getSession().getAttribute("user");
            }
			/* get user avatar path */
            if (user != null) {
                out.write(avatarManager.findPhotoRelativePath(user.getId()));
                out.write("' alt='");
                out.write(user.getUsername());
            } else {
                out.write("' alt='");
                out.write("no image");
            }
            out.write("'");
        } catch (IOException e) {
            throw new JspTagException(e.getMessage());
        }
        return SKIP_BODY;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }
}
