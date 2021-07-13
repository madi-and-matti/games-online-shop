import '../games.scss';

import React, {useState} from "react";
import {Button, ButtonDropdown, Col, DropdownMenu, DropdownToggle, Row, DropdownItem} from 'reactstrap';

export const CartQuantityBox = ({
                                  checkoutDisabled,
                                  quantity,
                                  desiredQuantity,
                                  setDesiredQuantity
                                }) => {
  const [dropdownOpen, setOpen] = useState(false);
  const toggle = () => setOpen(!dropdownOpen);

  const createQuantityDropdown = (quantityNum: number) => {
    const items = [];
    const upperBound = Math.min(quantityNum, 10);

    for (let i = 1; i <= upperBound; i++) {
      items.push(<DropdownItem key={i} onClick={() => {
        setDesiredQuantity(i)
      }}>{i}</DropdownItem>)
    }
    return items;
  }

  return (
    <Col className="cart-quantity-container col-4 offset-1 d-flex flex-column justify-content-between">
      <Row className="cart-button-div">
        {!checkoutDisabled && (
          <Col md="12 d-flex justify-content-center">
            <Button color="primary" className="mt-3 responsive-button bold-text" disabled={checkoutDisabled}>Add
              to
              Cart</Button>
          </Col>
        )}
        {checkoutDisabled && (
          <Col md="12 d-flex justify-content-center">
            <Button color="primary" className="mt-3 responsive-button bold-text" disabled={checkoutDisabled}>Out
              of Stock</Button>
          </Col>
        )}
      </Row>
      <Row>
        {quantity >= 1 && (
          <Col className="d-flex align-items-end justify-content-center mb-2">
            <div className="mb-2 pr-2 bold-text">
              Quantity
            </div>
            <ButtonDropdown isOpen={dropdownOpen} toggle={toggle} disabled={checkoutDisabled}>
              <DropdownToggle caret color="light"
                              className="toggle-dropdown">{desiredQuantity}</DropdownToggle>
              <DropdownMenu>
                {quantity && createQuantityDropdown(quantity).map(item => item)}
              </DropdownMenu>
            </ButtonDropdown>
          </Col>
        )}
      </Row>
    </Col>
  )
}
