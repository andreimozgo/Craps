package by.mozgo.craps.command;

import by.mozgo.craps.command.admin.AdminPageCommand;
import by.mozgo.craps.command.admin.ChangeRoleCommand;
import by.mozgo.craps.command.admin.DeleteUserCommand;
import by.mozgo.craps.command.admin.ShowUsersCommand;
import by.mozgo.craps.command.player.*;
import by.mozgo.craps.command.user.AddRegistrationCommand;
import by.mozgo.craps.command.user.LoginCommand;
import by.mozgo.craps.command.user.LogoutCommand;
import by.mozgo.craps.command.user.RegistrationCommand;

public enum CommandEnum {
    LOGIN {
        {
            this.command = new LoginCommand();
        }
    },
    LOGOUT {
        {
            this.command = new LogoutCommand();
        }
    },
    CHANGEROLE {
        {
            this.command = new ChangeRoleCommand();
        }
    },
    REGISTRATION {
        {
            this.command = new RegistrationCommand();
        }
    },
    ADDREGISTRATION {
        {
            this.command = new AddRegistrationCommand();
        }
    },
    DELETEUSER {
        {
            this.command = new DeleteUserCommand();
        }
    },
    PLAY {
        {
            this.command = new PlayCommand();
        }
    },
    PAY {
        {
            this.command = new PayCommand();
        }
    },
    CHANGEPWD {
        {
            this.command = new ChangePwdCommand();
        }
    },
    STATS {
        {
            this.command = new StatsCommand();
        }
    },
    PLAYERPAGE {
        {
            this.command = new PlayerPageCommand();
        }
    },
    SHOWUSERS {
        {
            this.command = new ShowUsersCommand();
        }
    },
    ADMINPAGE {
        {
            this.command = new AdminPageCommand();
        }
    };

    ActionCommand command;

    public ActionCommand getCurrentCommand() {
        return command;
    }
}

