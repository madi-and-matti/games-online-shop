import '../games.scss';

import {RouteComponentProps} from "react-router-dom";
import {connect} from "react-redux";
import {IRootState} from "app/shared/reducers";
import {getSingleGame} from "app/modules/games/games.reducer";
import React, {useEffect, useState} from "react";
import {Col, Container, DropdownItem, Row} from 'reactstrap';
import {cleanText, formatCurrency} from "app/shared/util/render-utils";
import {CartQuantityBox} from "app/modules/games/single-game/cart-quantity-box";
import {CategoriesMechanics} from "app/modules/games/single-game/categories-mechanics";
import {Carousel} from "app/modules/games/single-game/carousel";

export interface ISingleGameProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {
}

export const SingleGame = (props: ISingleGameProps) => {
  const [formattedCurrency, setFormattedCurrency] = useState("");
  const [quantityDropdown, setQuantityDropdown] = useState([]);
  const [desiredQuantity, setDesiredQuantity] = useState(1);
  const [checkoutDisabled, setCheckoutDisabled] = useState(false);

  const createQuantityDropdown = (quantity: number) => {
    const items = [];
    if (quantity <= 10) {
      for (let i = 1; i <= quantity; i++) {
        items.push(<DropdownItem onClick={() => {
          setDesiredQuantity(i)
        }}>{i}</DropdownItem>)
      }
    } else {
      for (let i = 1; i <= 10; i++) {
        items.push(<DropdownItem onClick={() => {
          setDesiredQuantity(i)
        }}>{i}</DropdownItem>)
      }
    }
    return items;
  }

  useEffect(() => {
    props.getSingleGame(props.match.params.id);
  }, []);
  const {
    name, price, quantity, rulesUrl, largeImageUrl, description, categories, mechanics,
    yearPublished, minimumAge, minimumPlayers, maximumPlayers, userRating
  } = props.singleGame;

  useEffect(() => {
    setFormattedCurrency(formatCurrency(price))
  }, [price]);

  useEffect(() => {
    setQuantityDropdown(createQuantityDropdown(quantity))
    if (quantity === 0) {
      setCheckoutDisabled(true)
    } else {
      setCheckoutDisabled(false)
    }
  }, [quantity]);

  useEffect(() => {
  }, [desiredQuantity])

  // eslint-disable-next-line no-console
  return (
    <div>
      <Container className="body-div mt-5" fluid={true}>
        <Row className="info-div d-flex justify-content-between">
          {largeImageUrl && (
            <Col className="image-div offset-1" lg="4" sm="6">
              <img src={largeImageUrl}
                   alt="game image"
                   className="single-game-img"
              />
            </Col>
          )}
          <Col className="content-div col-6">
            <Row className="title-and-cart-button-div mb-4 d-flex">
              <Col className="name-and-price-div col-5">
                {name && (
                  <h1>{name}</h1>
                )}
                {formattedCurrency && (
                  <p className="mt-3 bold-text">{formattedCurrency}</p>
                )}
              </Col>
              <CartQuantityBox checkoutDisabled={checkoutDisabled}
                               quantity={quantity}
                               quantityDropdown={quantityDropdown}
                               desiredQuantity={desiredQuantity}
              />
            </Row>
            {description && (
              <div className="description col-9 ml-0 pl-0 new-line"
                   dangerouslySetInnerHTML={{__html: cleanText(description.text)}}/>
            )}
            <CategoriesMechanics categories={categories} mechanics={mechanics}/>
            <Row className="mt-4">
              {
                rulesUrl && (
                  <a href={rulesUrl} className="link">Official Rules</a>
                )
              }
            </Row>
          </Col>
        </Row>
        <Carousel
          maximumPlayers={maximumPlayers}
          minimumPlayers={minimumPlayers}
          minimumAge={minimumAge}
          userRating={userRating}
          yearPublished={yearPublished}/>
      </Container>
    </div>
  )
}

const mapStateToProps = (storeState: IRootState) => ({
  singleGame: storeState.games.singleGame,
});

const mapDispatchToProps = {getSingleGame};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SingleGame);
