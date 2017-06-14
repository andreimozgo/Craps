package by.mozgo.craps.command;

import by.mozgo.craps.command.admin.AdminPageCommand;
import by.mozgo.craps.command.admin.DeleteUserCommand;
import by.mozgo.craps.command.client.ClientPageCommand;
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
    ADDFLIGHT {
        {
            //          this.command = new AddFlightCommand();
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
    RECALCULATE {
        {
            //           this.command = new RecalculateCommand();
        }
    },
    BUYTICKET {
        {
            //           this.command = new BuyTicketCommand();
        }
    },
    PAYTICKET {
        {
//            this.command = new PayTicketCommand();
        }
    },
    CLIENTPAGE {
        {
            this.command = new ClientPageCommand();
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

