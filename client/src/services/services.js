import axios from 'axios';

const SERVER = "http://localhost:8080/api/blackjack/"

class Services {
    static sendHitAction(index) {
        axios.post(SERVER +"hit/"+ index, {})
        .then(res => {
            console.log(res);
            console.log(res.data);
      })
    }

    static sendStandAction(index) {
        axios.post(SERVER +"stand/"+ index, {})
        .then(res => {
            console.log(res);
            console.log(res.data);
      })
    }

    static sendSplitAction(index) {
        axios.post(SERVER +"split/"+ index, {})
        .then(res => {
            console.log(res);
            console.log(res.data);
      })
    }

    static sendDoubleDownAction(index) {
        axios.post(SERVER +"doubleDown/"+ index, {})
        .then(res => {
            console.log(res);
            console.log(res.data);
      })
    }

    static sendNewGameRequest(betAmount) {
        axios.post(SERVER +"newGame/" + betAmount, {})
        .then(res => {
            console.log("new game created");
            console.log(res.data);
      })
    }

    static sendNewMatchRequest(betAmount) {
        axios.post(SERVER, {})
        .then(res => {
            console.log("new match created");
      })
    }

    static mapResponseToContext(res) {
        
    }
}

export default Services;