package br.eti.augusto.transactional;

import javax.transaction.Transactional;

@Transactional(value = Transactional.TxType.REQUIRES_NEW)
public class BeanRequiresNew extends BeanBase {
}
