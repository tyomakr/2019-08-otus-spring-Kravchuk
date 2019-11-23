import React from 'react'
import {Helmet} from "react-helmet/es/Helmet";
import {Header} from "../fragments/Header";
import axios from "axios";

export default class BookUpdate extends React.Component {


    constructor(props) {
        super(props);
        this.state = {
            id: '',
            title: '',
            authors: [],
            genres: []
        };
        this.getBook = this.getBook.bind(this);
        this.showDeleteAuthorButton = this.showDeleteAuthorButton.bind(this);
        this.showDeleteGenreButton = this.showDeleteGenreButton.bind(this);
        this.saveBook = this.saveBook.bind(this);
    }


    componentDidMount() {
        this.getBook();
    }

    //Получение книг по rest
    getBook() {
        axios.get('/api/v1/books/' + window.localStorage.getItem("id"))
            .then((res) => {
                let book = res.data;
                this.setState({
                    id: book.id,
                    title: book.title,
                    authors : book.authors,
                    genres : book.genres,
                })
            });
    }


    //onChange
    handleChange(e) {
        e.preventDefault();
        this.setState({[e.target.name] : e.target.value });
        console.log(e)
    }


    //сохранить книгу
    saveBook(e) {
        console.log(this.state.authors);
        e.preventDefault();
        let book = {
            id: this.state.id,
            title: this.state.title,
            authors: this.state.authors,
            genres: this.state.genres
        };
        console.log(book)
        axios.put('/api/v1/books/update/' + book.id, book);

        this.getBook();
    }



    //показывает кнопку удаления автора, если их больше одного
    showDeleteAuthorButton() {
        return this.state.authors.length > 1;
    }


    //показывает кнопку удаления жанра, если их больше одного
    showDeleteGenreButton() {
        return this.state.genres.length > 1;
    }


    handleAuthorChange(e) {

    }

    handleGenreChange(field, name, e) {
        e.preventDefault();
        this.setState({[e.target.name] : e.target.value });
        console.log(e)
    }



    render() {

        return (
            <React.Fragment>
                <div className={BookUpdate}>
                    <Helmet>
                        <meta charSet="utf-8" />
                        <title>React Spring Library - Редактирование книги</title>
                    </Helmet>
                </div>
                <Header history={this.props.history}/>

                <div className="container mt-3 ml-3">
                    <h2>Редактирование книги</h2>
                </div>
                <div className="container mt-3 ml-3">
                    <form>
                        <div className="form-group input-group mt-3">
                            <label className="col-sm-2 col-form-label" for="id">ID</label>
                            <div className="col-sm-10">
                                <input className="form-control-plaintext" type="text" id="id" name="id" readOnly="true" defaultValue={this.state.id} />
                            </div>
                        </div>

                        <div className="form-group input-group mt-3">
                            <label className="col-sm-2 col-form-label" for="title">Название книги</label>
                            <input className="form-control" type="text" id="title" name="title" defaultValue={this.state.title} onChange={this.handleChange.bind(this)}/>
                        </div>
                        {
                            this.state.authors.map(author =>
                                <div key={author.id} className="input-group-append mt-3 mb-3">
                                    <label className="col-sm-2 col-form-label">Автор</label>
                                    <input className="form-control" id={author.authorName} name={author.authorName}
                                           defaultValue={author.authorName} onChange={event => this.setState({authorName : event.target.value})}/>
                                    {
                                        this.showDeleteAuthorButton() &&

                                        <div className="input-group-append">
                                            <button className="btn btn-outline-danger">remove</button>
                                        </div>
                                    }
                                </div>
                            )
                        }
                        {
                            Object.keys(this.state.genres).map( (genre, i) =>
                            <div key={i} className="input-group-append mt-3 mb-3">
                                <label className="col-sm-2 col-form-label">Жанр</label>

                                <input className="form-control" id={i} name={i}
                                       defaultValue={genre.genreTitle} onChange={this.handleGenreChange.bind(this, genre.title)}/>

                                {
                                    this.showDeleteGenreButton() &&

                                    <div className="input-group-append">
                                        <button className="btn btn-outline-danger">remove</button>
                                    </div>
                                }
                            </div>
                            )


                            // this.state.genres.map( genre =>
                            // <div key={genre.id} className="input-group-append mt-3 mb-3">
                            //     <label className="col-sm-2 col-form-label">Жанр</label>
                            //
                            //     <input className="form-control" id={genre.id} name={genre.id}
                            //            defaultValue={genre.genreTitle} onChange={this.handleGenreChange.bind(this, genre.title)}/>
                            //
                            //     {
                            //         this.showDeleteGenreButton() &&
                            //
                            //         <div className="input-group-append">
                            //             <button className="btn btn-outline-danger">remove</button>
                            //         </div>
                            //     }
                            // </div>
                            // )
                        }

                        <button className="btn btn-success" onClick={this.saveBook}>Save</button>
                    </form>
                </div>
            </React.Fragment>
        )
    }
}