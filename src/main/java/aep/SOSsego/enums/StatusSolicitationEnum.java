package aep.SOSsego.enums;

public enum StatusSolicitationEnum {
    ABERTO,
    TRIAGEM,
    EM_EXECUCAO,
    RESOLVIDO,
    ENCERRADO;

    public boolean canTransitionTo(StatusSolicitationEnum next) {
        return switch (this) {
            case ABERTO -> next == TRIAGEM;
            case TRIAGEM -> next == EM_EXECUCAO || next == ENCERRADO;
            case EM_EXECUCAO -> next == RESOLVIDO;
            case RESOLVIDO -> next == ENCERRADO;
            case ENCERRADO -> false;
        };
    }
}
