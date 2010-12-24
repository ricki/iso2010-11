package es.uclm.iso2.rmi;

class UserAlreadyExistsException extends Exception {

	private static final long serialVersionUID = -4971949997653617881L;
}

class WrongLoginException extends Exception {

	private static final long serialVersionUID = 4537478030192183927L;
}

class InvalidSessionException extends Exception {

	private static final long serialVersionUID = -7260194304362605883L;
}

class InvalidGameInfoException extends Exception {

	private static final long serialVersionUID = 1882941311918311068L;
}

class FullGameException extends Exception {

	private static final long serialVersionUID = -1845472957679933689L;
}

class GameNotFoundException extends Exception {

	private static final long serialVersionUID = 171130139931367524L;

}

class InvalidTerritoryException extends Exception {

	private static final long serialVersionUID = 1770410235259899266L;
}

class NotCurrentPlayerGameException extends Exception {

	private static final long serialVersionUID = -566764146183429953L;
}

class InvalidTimeException extends Exception {

	private static final long serialVersionUID = -8416290514218785629L;
}

class InvalidArsenalException extends Exception {

	private static final long serialVersionUID = 3041758119564433940L;
}
