import {RouteComponentProps} from "react-router-dom";
import {connect} from "react-redux";
import {IRootState} from "app/shared/reducers";
import {ACTION_TYPES, getSingleGame} from "app/modules/games/games.reducer";
import React, {useEffect, useState} from "react";
import {Container, Row, Col, Button} from 'reactstrap';

export interface ISingleGameProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {
}

export const SingleGame = (props: ISingleGameProps) => {
  const [imgHeight, setImgHeight] = useState(0);
  const [imgWidth, setImgWidth] = useState(0);
  useEffect(() => {
    props.getSingleGame(props.match.params.id);
  }, []);
  // const {name, price, quantity, rulesUrl, largeImageUrl, description} = props.singleGame;
  // eslint-disable-next-line no-console
  return (
    <div>
    <Container className="body-div">
      <Row className="info-div" style={{display: 'flex'}}>
        {/*{largeImageUrl && (*/}
        <Col className="image-div" sm={{offset: 1}} style={{border: '1px solid green'}}>
          <img src="https://d2k4q26owzy373.cloudfront.net/700x700/games/uploaded/1547618688072"
               alt="game image"
               style={{filter: 'drop-shadow(-15px -15px 0 #D5CAFF)', border: '1px solid black'}}
          />
        </Col>
        {/*)*/}
        {/*}*/}
        <Col className="content-div" style={{border: '1px solid pink'}}>
          <Row className="title-and-cart-button-div" style={{display: 'flex'}}>
            <Col className="name-and-price-div">
              <h1>Zombie Plague</h1>
              <p>1200</p>
            </Col>
            <Col className="cart-button-div">
              <Button color="primary">Add to Cart</Button>
              <p>Quantity: 12</p>
            </Col>
          </Row>
          <div className="description">
            Somewhat reminiscent of the Dawn of the Dead board game, on one team are the humans, while on the other are
            the zombies. The humans race to barricade all the doors and windows, while searching the house for helpful
            items. The zombies, on the other hand, just want to eat those tasty humans.
          </div>
          <div className="categories-and-mechanics-div">
            <div className="categories"></div>
            <div className="mechanics"></div>
          </div>
          {/*{*/}
          {/*  rulesUrl && <a href={rulesUrl}>Official Rules</a>*/}
          {/*}*/}
          <a href="https://www.dropbox.com/s/x9xzsbfvqdpjo33/CTRL_Rulebook_Web.pdf?dl=0">Official Rules</a>
        </Col>
      </Row>
      <div className="carousel-div"></div>
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
