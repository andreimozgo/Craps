package by.mozgo.craps.customtag;

import by.mozgo.craps.entity.User;
import by.mozgo.craps.manager.AvatarManager;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * Custom tag to define user avatar path and output it as &lt;img&gt; tag
 *
 * @author qqq175
 */
public class UserAvatarTag extends TagSupport {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private User user;

    /*
     * (non-Javadoc)
     *
     * @see javax.servlet.jsp.tagext.TagSupport#doEndTag()
     */
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

    /*
     * (non-Javadoc)
     *
     * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
     */
    @Override
    public int doStartTag() throws JspException {
        AvatarManager avatarManager = new AvatarManager(pageContext.getServletContext());
        try {
            JspWriter out = pageContext.getOut();
            out.write("<img src='");
			/* if user param isn't defined - take user from session */
            if (user == null) {
                user = (User) pageContext.getSession().getAttribute("user");
                System.out.println("TAG user - session");
            }
			/* get user avatar path */
            if (user != null) {
                out.write(avatarManager.findPhotoRelativePath(user.getId()));
                out.write("' alt='");
                out.write(user.getUsername());
            } else {
                out.write("' alt='");
                out.write("no image");
                System.out.println("TAG user - null");
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
