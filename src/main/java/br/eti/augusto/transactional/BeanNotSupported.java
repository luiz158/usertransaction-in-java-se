package br.eti.augusto.transactional;

import javax.transaction.Transactional;

@Transactional(value = Transactional.TxType.NOT_SUPPORTED)
public class BeanNotSupported extends BeanBase {
}
