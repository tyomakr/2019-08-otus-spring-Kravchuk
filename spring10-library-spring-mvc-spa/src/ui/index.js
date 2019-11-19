import React from 'react'
import ReactDOM from 'react-dom'
import App from './components/App'
import BrowserRouter from "react-router-dom/es/BrowserRouter";
import Switch from "react-router-dom/es/Switch";
import Route from "react-router-dom/es/Route";
import Authors from "./components/Authors";
import Genres from "./components/Genres";
import BookCreate from "./components/BookCreate";
import Books from "./components/Books";
import BookEdit from "./components/BookEdit";

ReactDOM.render(
    <BrowserRouter>
        <Switch>
            <Route exact path="/" component={App} />
            <Route exact path="/Books" component={Books} />
            <Route exact path="/Books/Add" component={BookCreate} />
            <Route exact path="/Books/edit/:id" component={BookEdit}/>
            <Route exact path="/Authors" component={Authors} />
            <Route exact path="/Genres" component={Genres} />
        </Switch>
    </BrowserRouter>,
    document.getElementById('root')
);